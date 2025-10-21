package com.microbio.infrastructure.persistence.adapter;

import com.microbio.application.port.out.AnalysisRepository;
import com.microbio.domain.model.Analysis;
import com.microbio.infrastructure.mapper.EntityDomainMapper;
import com.microbio.infrastructure.persistence.entity.AnalysisEntity;
import com.microbio.infrastructure.persistence.repo.AnalysisPanacheRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.smallrye.mutiny.converters.uni.UniRx3Converters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class AnalysisRepositoryAdapter implements AnalysisRepository {

    @Inject AnalysisPanacheRepository repo;
    @Inject EntityDomainMapper mapper;

    @Override
    public Single<Analysis> insert(Analysis a) {
        AnalysisEntity entity = mapper.toEntity(a);
        entity.createdAt = java.time.Instant.now();

        return repo.persist(entity)
                .replaceWith(entity) // Uni<AnalysisEntity>
                .map(mapper::toDomain) // Uni<Analysis>
                .onItem().ifNull().failWith(() -> new RuntimeException("Entity not found"))
                .convert().with(UniRx3Converters.toSingle());
    }

    @Override
    public Flowable<Analysis> findByPatient(String patientId, int limit) {
        Publisher<Analysis> pub = repo.find("patientId", new ObjectId(patientId))
                .page(0, limit)
                .stream() // Multi<AnalysisEntity>
                .map(mapper::toDomain)
                .toPublisher();

        return Flowable.fromPublisher(pub);
    }

    @Override
    public Maybe<Analysis> findLatestByPatient(String patientId) {
        return repo.find("patientId", new ObjectId(patientId))
                .page(0, 1)
                .firstResult() // Uni<AnalysisEntity>
                .map(mapper::toDomain) // Uni<Analysis>
                .onItem().ifNull().failWith(() -> new RuntimeException("Entity not found"))
                .convert().with(UniRx3Converters.toMaybe());
    }
}