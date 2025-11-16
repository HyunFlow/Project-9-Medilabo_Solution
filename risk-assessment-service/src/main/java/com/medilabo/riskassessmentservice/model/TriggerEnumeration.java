package com.medilabo.riskassessmentservice.model;

import lombok.Getter;

@Getter
public enum TriggerEnumeration {
    HEMOGLOBINE_A1C("hémoglobine A1C"),
    MICROALBUMINE("microalbumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur"),
    FUMEUSE("fumeuse"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholestérol"),
    VERTIGE("vertige"),
    RECHUTE("rechute"),
    REACTION("réaction"),
    ANTICORPS("anticorps");

    private final String value;

    TriggerEnumeration(String value) {
        this.value = value;
    }
}