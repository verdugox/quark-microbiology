package com.microbio.infrastructure.persistence.repo;

import com.microbio.infrastructure.persistence.entity.AnalysisEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnalysisPanacheRepository implements ReactivePanacheMongoRepository<AnalysisEntity> {}
