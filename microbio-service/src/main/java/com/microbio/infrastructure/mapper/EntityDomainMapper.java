package com.microbio.infrastructure.mapper;

import com.microbio.domain.model.Analysis;
import com.microbio.domain.model.Patient;
import com.microbio.infrastructure.persistence.entity.AnalysisEntity;
import com.microbio.infrastructure.persistence.entity.PatientEntity;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "cdi", imports = {ObjectId.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface EntityDomainMapper {

    // Patient
    @Mapping(target = "id", expression = "java(e.getId() != null ? e.getId().toString() : null)")
    @Mapping(target = "lastAnalysisId", expression = "java(e.getLastAnalysisId() != null ? e.getLastAnalysisId().toString() : null)")
    Patient toDomain(PatientEntity e);

    @Mapping(target = "id", expression = "java(d.getId() != null ? new ObjectId(d.getId()) : null)")
    @Mapping(target = "lastAnalysisId", expression = "java(d.getLastAnalysisId() != null ? new ObjectId(d.getLastAnalysisId()) : null)")
    PatientEntity toEntity(Patient d);

    // Analysis
    @Mapping(target = "id", expression = "java(e.getId() != null ? e.getId().toString() : null)")
    @Mapping(target = "patientId", expression = "java(e.getPatientId() != null ? e.getPatientId().toString() : null)")
    Analysis toDomain(AnalysisEntity e);

    @Mapping(target = "id", expression = "java(d.getId() != null ? new ObjectId(d.getId()) : null)")
    @Mapping(target = "patientId", expression = "java(d.getPatientId() != null ? new ObjectId(d.getPatientId()) : null)")
    AnalysisEntity toEntity(Analysis d);
}

