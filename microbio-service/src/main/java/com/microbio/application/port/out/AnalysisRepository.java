package com.microbio.application.port.out;

import com.microbio.domain.model.Analysis;
import io.reactivex.rxjava3.core.*;

import java.util.Optional;

public interface AnalysisRepository {
    Single<Optional<Analysis>> insert(Analysis a);
    Flowable<Analysis> findByPatient(String patientId, int limit);
    Maybe<Analysis> findLatestByPatient(String patientId);
}
