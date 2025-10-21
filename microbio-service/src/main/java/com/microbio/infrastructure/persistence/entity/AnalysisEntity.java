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

@MongoEntity(collection="analyses")
public class AnalysisEntity extends PanacheMongoEntityBase {
    public ObjectId id;
    public ObjectId patientId;
    public SampleEmb sample;
    public Instant analyzedAt;
    public String machineModel;
    public String runId;
    public List<IsolateEmb> isolates;
    public String notes;
    public Instant createdAt;

    public static class SampleEmb { public String type; public Instant collectedAt; }
    public static class IsolateEmb {
        public ObjectId organismId; public String organismName;
        public java.util.List<SuscEmb> susceptibilities; public String notes;
    }
    public static class SuscEmb {
        public ObjectId antibioticId; public String abbr; public Double mic; public String result;
    }
}
