package com.microbio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    private String id;
    private String patientId;
    private String runId;
    private Instant createdAt;
    private String result;
    private String status;
}
