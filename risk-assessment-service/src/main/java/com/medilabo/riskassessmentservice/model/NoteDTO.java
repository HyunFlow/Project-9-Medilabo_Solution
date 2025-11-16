package com.medilabo.riskassessmentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NoteDTO {
    private String id;
    private Integer patId;
    private String patient;
    @JsonProperty("note")
    private String content;
}
