package com.microbio.infrastructure.persistence.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.types.ObjectId;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@MongoEntity(collection="patients")
public class PatientEntity extends PanacheMongoEntityBase {
    public ObjectId id;
    public String dni;
    public String name;
    public String lastName;
    public List<String> allergies;
    public ObjectId lastAnalysisId;
    public Instant createdAt;
    public Instant updatedAt;
}
