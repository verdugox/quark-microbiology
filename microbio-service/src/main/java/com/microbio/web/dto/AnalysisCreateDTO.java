package com.microbio.web.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;

public class AnalysisCreateDTO {
    @NotBlank public String patientId;
    @NotNull  public SampleDTO sample;
    @NotNull  public Instant analyzedAt;
    public String machineModel; public String runId; public String notes;
    @Size(max=20) public List<IsolateDTO> isolates;

    public static class SampleDTO { @NotBlank public String type; public Instant collectedAt; }
    public static class IsolateDTO {
        public String organismId; public String organismName;
        public java.util.List<SuscDTO> susceptibilities; public String notes;
    }
    public static class SuscDTO { public String antibioticId; public String abbr; public Double mic; public String result; }
}
