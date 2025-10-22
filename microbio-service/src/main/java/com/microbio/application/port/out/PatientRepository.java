package com.microbio.application.port.out;

import com.microbio.domain.model.Patient;
import io.reactivex.rxjava3.core.*;

import java.util.Optional;

public interface PatientRepository {
    Single<Optional<Patient>> upsert(Patient p);
    Maybe<Patient> findById(String id);
    Maybe<Patient> findByDni(String dni);
    Completable updateLastAnalysis(String patientId, String analysisId);
}
