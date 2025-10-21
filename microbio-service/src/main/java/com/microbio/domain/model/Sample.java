package com.microbio.domain.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Sample {
    public enum Type { blood, urine, sputum, wound, other }
    private Type type;
    private Instant collectedAt;

    // getters/setters
}
