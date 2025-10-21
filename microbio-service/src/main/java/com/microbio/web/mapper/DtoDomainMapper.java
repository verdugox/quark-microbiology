package com.microbio.web.mapper;

import com.microbio.domain.model.*;
import com.microbio.domain.value.Result;
import com.microbio.web.dto.AnalysisCreateDTO;
import com.microbio.web.dto.AnalysisDTO;
import org.mapstruct.*;

@Mapper(componentModel = "cdi", imports = Result.class)
public interface DtoDomainMapper {

    Analysis toDomain(AnalysisCreateDTO dto);

    AnalysisDTO toDto(Analysis domain);
}
