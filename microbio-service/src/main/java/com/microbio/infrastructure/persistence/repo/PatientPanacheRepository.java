package com.microbio.infrastructure.persistence.repo;

import com.microbio.infrastructure.persistence.entity.PatientEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase; // 👈
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PatientPanacheRepository implements ReactivePanacheMongoRepositoryBase<PatientEntity, ObjectId> {
}
