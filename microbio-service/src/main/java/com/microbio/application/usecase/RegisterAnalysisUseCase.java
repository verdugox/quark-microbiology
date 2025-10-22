package com.microbio.application.usecase;

import com.microbio.application.port.out.AnalysisRepository;
import com.microbio.application.port.out.Notifier;
import com.microbio.application.port.out.PatientRepository;
import com.microbio.domain.model.Analysis;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class RegisterAnalysisUseCase {

    @Inject
    AnalysisRepository analysisRepo;
    @Inject
    PatientRepository patientRepo;
    @Inject
    Notifier notifier;

    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2000)
    @Retry(maxRetries = 2, delay = 300)
    public Single<Analysis> execute(Analysis analysis) {
        return analysisRepo.insert(analysis)
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Single.error(new RuntimeException("Analysis insert returned empty Optional"));
                    }
                    Analysis a = optional.get();

                    // primero actualiza el paciente
                    return patientRepo.updateLastAnalysis(a.getPatientId(), a.getId())
                            .andThen(Single.just(a));
                })
                .flatMap(a ->
                        // luego dispara notificaciones
                        Completable.mergeArray(
                                notifier.emailAnalysisCreated(a),
                                notifier.publishAnalysisEvent(a)
                        ).andThen(Single.just(a))
                );
    }
}
