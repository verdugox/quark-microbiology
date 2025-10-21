package com.microbio.infrastructure.persistence.adapter;

import com.microbio.application.port.out.PatientRepository;
import com.microbio.domain.model.Patient;
import com.microbio.infrastructure.mapper.EntityDomainMapper;
import com.microbio.infrastructure.persistence.entity.PatientEntity;
import com.microbio.infrastructure.persistence.repo.PatientPanacheRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.smallrye.mutiny.converters.uni.UniRx3Converters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PatientRepositoryAdapter implements PatientRepository {

    @Inject PatientPanacheRepository repo;
    @Inject EntityDomainMapper mapper;

    @Override
    public Single<Patient> upsert(Patient p) {
        PatientEntity entity = mapper.toEntity(p);
        entity.updatedAt = java.time.Instant.now();
        if (entity.createdAt == null) entity.createdAt = entity.updatedAt;

        // persistOrUpdate -> Uni<PatientEntity>
        return repo.persistOrUpdate(entity)
                .map(mapper::toDomain)
                .convert().with(UniRx3Converters.<Patient>toSingle());
    }

    @Override
    public Maybe<Patient> findById(String id) {
        return repo.findById(new ObjectId(id))
                .map(e -> java.util.Optional.ofNullable(e).map(mapper::toDomain))
                .convert().with(UniRx3Converters.toMaybe());
    }

    @Override
    public Maybe<Patient> findByDni(String dni) {
        return repo.find("dni", dni)
                .firstResult()
                .map(e -> java.util.Optional.ofNullable(e).map(mapper::toDomain))
                .convert().with(UniRx3Converters.toMaybe());
    }

    @Override
    public Completable updateLastAnalysis(String patientId, String analysisId) {
        return repo.update("lastAnalysisId = ?1 where id = ?2",
                        new ObjectId(analysisId), new ObjectId(patientId))
                .replaceWithVoid()
                .convert().with(UniRx3Converters.toCompletable());
    }
}
