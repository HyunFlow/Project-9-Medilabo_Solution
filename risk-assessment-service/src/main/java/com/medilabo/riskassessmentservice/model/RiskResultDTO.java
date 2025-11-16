package com.medilabo.riskassessmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskResultDTO {

    private Integer patId;
    private String firstName;
    private String lastName;
    private Integer age;
    private int triggerCount;
    private RiskLevel riskLevel;

}
