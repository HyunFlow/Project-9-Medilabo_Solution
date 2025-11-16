package com.medilabo.riskassessmentservice.model;

import lombok.Data;

@Data
public class RiskResultDTO {
    private int level;
    private String name;

    public RiskResultDTO(int level, String name) {
        this.level = level;
        this.name = name;
    }
}
