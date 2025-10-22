package com.microbio.infrastructure.persistence.adapter;

import com.microbio.application.port.out.AnalysisRepository;
import com.microbio.domain.model.Analysis;
import com.microbio.infrastructure.mapper.EntityDomainMapper;
import com.microbio.infrastructure.persistence.entity.AnalysisEntity;
import com.microbio.infrastructure.persistence.repo.AnalysisPanacheRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.smallrye.mutiny.converters.multi.MultiRx3Converters;
import io.smallrye.mutiny.converters.uni.UniRx3Converters;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.Optional;

@ApplicationScoped
public class AnalysisRepositoryAdapter implements AnalysisRepository {

    @Inject AnalysisPanacheRepository repo;
    @Inject EntityDomainMapper mapper;

    @Override
    public Single<Optional<Analysis>> insert(Analysis analysis) {
        AnalysisEntity entity = mapper.toEntity(analysis);

        // persist -> Uni<Void>; replaceWith(entity) -> Uni<AnalysisEntity>; map -> Uni<Analysis>
        return repo.persist(entity)
                .replaceWith(entity)
                .onItem().transform(mapper::toDomain)
                .onItem().ifNull().failWith(() -> new RuntimeException("Mapper returned null for AnalysisEntity"))
                .convert().with(UniRx3Converters.toSingle());
    }

    @Override
    public Flowable<Analysis> findByPatient(String patientId, int limit) {
        // usamos _id/creationTime implícito; si tienes un campo 'createdAt', ordénalo por él
        return repo.find("patientId", new ObjectId(patientId))
                .page(Page.of(0, limit))
                .stream()
                .onItem().transform(mapper::toDomain)
                .convert().with(MultiRx3Converters.toFlowable());
    }

    @Override
    public Maybe<Analysis> findLatestByPatient(String patientId) {
        // “último” por ObjectId descendente; si tienes 'createdAt', cambia a order("createdAt desc")
        return repo.find("patientId", new ObjectId(patientId))
                .page(Page.ofSize(1))
                .firstResult()
                .onItem().ifNotNull().transform(mapper::toDomain)
                .convert().with(UniRx3Converters.toMaybe());
    }
}
