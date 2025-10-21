package com.microbio.domain.model;

import com.microbio.domain.value.Result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Susceptibility {
    private String antibioticId;
    private String abbr;
    private Double mic;
    private Result result; // S/I/R
    // getters/setters
}
