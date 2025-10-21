package com.microbio.infrastructure.persistence.repo;

import com.microbio.infrastructure.persistence.entity.PatientEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientPanacheRepository implements ReactivePanacheMongoRepository<PatientEntity> {}
