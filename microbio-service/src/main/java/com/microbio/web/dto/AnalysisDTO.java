package com.microbio.web.dto;

import java.time.Instant;
import java.util.List;
public class AnalysisDTO {
    public String id; public String patientId; public Instant analyzedAt;
    public AnalysisCreateDTO.SampleDTO sample;
    public String machineModel; public String runId; public String notes;
    public List<AnalysisCreateDTO.IsolateDTO> isolates;
}
