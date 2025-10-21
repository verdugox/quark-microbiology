package com.microbio.application.usecase;

import com.microbio.application.port.out.AnalysisRepository;
import com.microbio.domain.model.Analysis;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetLatestAnalysisUseCase {

    @Inject
    AnalysisRepository repo;

    @CacheResult(cacheName = "latest-analysis")
    public Maybe<Analysis> execute(String patientId) {
        return repo.findLatestByPatient(patientId);
    }

    @CacheInvalidate(cacheName = "latest-analysis")
    public void evictCache(String patientId) {
        // invalida cache para este paciente
    }
}
