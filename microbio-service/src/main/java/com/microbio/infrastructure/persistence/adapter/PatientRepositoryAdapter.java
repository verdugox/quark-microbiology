package com.microbio.infrastructure.persistence.adapter;

import com.microbio.application.port.out.PatientRepository;
import com.microbio.domain.model.Patient;
import com.microbio.infrastructure.mapper.EntityDomainMapper;
import com.microbio.infrastructure.persistence.entity.PatientEntity;
import com.microbio.infrastructure.persistence.repo.PatientPanacheRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniRx3Converters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.Optional;

@ApplicationScoped
public class PatientRepositoryAdapter implements PatientRepository {

    @Inject PatientPanacheRepository repo;
    @Inject EntityDomainMapper mapper;

    @Override
    public Single<Optional<Patient>> upsert(Patient patient) {
        PatientEntity entity = mapper.toEntity(patient);

        Uni<PatientEntity> op = (entity.getId() != null)
                ? repo.update(entity).replaceWith(entity)
                : repo.persist(entity).replaceWith(entity);

        return op
                .onItem().transform(saved -> {
                    Optional<Patient> opt = Optional.ofNullable(mapper.toDomain(saved));
                    return opt.orElseThrow(() ->
                            new RuntimeException("Mapper returned null for PatientEntity"));
                })
                .convert().with(UniRx3Converters.toSingle());
    }

    @Override
    public Completable updateLastAnalysis(String patientId, String analysisId) {
        ObjectId pid = new ObjectId(patientId);
        ObjectId aid = new ObjectId(analysisId);

        return repo.findById(pid)
                .onItem().ifNull().failWith(() -> new RuntimeException("Patient not found"))
                .onItem().transform(entity -> {
                    entity.setLastAnalysisId(aid);
                    return entity;
                })
                .call(entity -> repo.update(entity))
                .replaceWithVoid()
                .convert().with(UniRx3Converters.toCompletable());
    }

    @Override
    public Maybe<Patient> findById(String id) {
        return repo.findById(new ObjectId(id))
                .onItem().transform(entity -> {
                    if (entity == null) return null;
                    Optional<Patient> opt = Optional.ofNullable(mapper.toDomain(entity));
                    return opt.orElse(null);
                })
                .convert().with(UniRx3Converters.toMaybe());
    }

    @Override
    public Maybe<Patient> findByDni(String dni) {
        return repo.find("dni", dni)
                .firstResult()
                .onItem().transform(entity -> {
                    if (entity == null) return null;
                    Optional<Patient> opt = Optional.ofNullable(mapper.toDomain(entity));
                    return opt.orElse(null);
                })
                .convert().with(UniRx3Converters.toMaybe());
    }
}
