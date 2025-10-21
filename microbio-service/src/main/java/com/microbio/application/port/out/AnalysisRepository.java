package com.microbio.application.port.out;

import com.microbio.domain.model.Analysis;
import io.reactivex.rxjava3.core.*;

public interface AnalysisRepository {
    Single<Analysis> insert(Analysis a);
    Flowable<Analysis> findByPatient(String patientId, int limit);
    Maybe<Analysis> findLatestByPatient(String patientId);
}
