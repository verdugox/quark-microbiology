package com.microbio.domain.model;

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

public class Patient {
    private String id;
    private String dni;
    private String name;
    private String lastName;
    private List<String> allergies;
    private String lastAnalysisId;
    private Instant createdAt;
    private Instant updatedAt;

    // getters/setters + ctor builder si quieres
}
