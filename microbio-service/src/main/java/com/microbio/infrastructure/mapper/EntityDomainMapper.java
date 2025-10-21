package com.microbio.infrastructure.mapper;

import com.microbio.domain.model.Analysis;
import com.microbio.domain.model.Patient;
import com.microbio.domain.value.Result;
import com.microbio.infrastructure.persistence.entity.AnalysisEntity;
import com.microbio.infrastructure.persistence.entity.PatientEntity;
import org.bson.types.ObjectId;
import org.mapstruct.*;

@Mapper(componentModel = "cdi", imports = {ObjectId.class, Result.class})
public interface EntityDomainMapper {

    // ---------- Patient ----------
    @Mapping(target = "id", expression = "java(p.getId() != null ? p.getId().toString() : null)")
    @Mapping(target = "lastAnalysisId", expression = "java(p.getLastAnalysisId() != null ? p.getLastAnalysisId().toString() : null)")
    Patient toDomain(PatientEntity p);

    @InheritInverseConfiguration(name = "toDomain")
    @Mapping(target = "id", expression = "java(p.getId() != null ? new ObjectId(p.getId()) : null)")
    @Mapping(target = "lastAnalysisId", expression = "java(p.getLastAnalysisId() != null ? new ObjectId(p.getLastAnalysisId()) : null)")
    PatientEntity toEntity(Patient p);

    // ---------- Analysis ----------
    @Mapping(target = "id", expression = "java(a.getId() != null ? a.getId().toString() : null)")
    @Mapping(target = "patientId", expression = "java(a.getPatientId() != null ? a.getPatientId().toString() : null)")
    Analysis toDomain(AnalysisEntity a);

    @InheritInverseConfiguration(name = "toDomain")
    @Mapping(target = "id", expression = "java(a.getId() != null ? new ObjectId(a.getId()) : null)")
    @Mapping(target = "patientId", expression = "java(a.getPatientId() != null ? new ObjectId(a.getPatientId()) : null)")
    AnalysisEntity toEntity(Analysis a);
}
