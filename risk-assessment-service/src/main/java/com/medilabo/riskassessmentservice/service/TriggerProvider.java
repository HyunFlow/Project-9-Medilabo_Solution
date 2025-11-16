package com.medilabo.riskassessmentservice.service;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Component
public class TriggerProvider {
    private static final List<String> TRIGGERS = List.of(
            "hémoglobine A1C",
            "microalbumine",
            "taille",
            "poids",
            "fumeur",
            "fumeuse",
            "anormal",
            "cholestérol",
            "vertiges",
            "rechute",
            "réaction",
            "anticorps"
    );

    public int countTriggers(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        String normalized = normalize(text);
        int count = 0;
        for (String trigger : TRIGGERS) {
            if (normalized.contains(trigger)) {
                count++;
            }
        }
        return count;
    }
    private String normalize(String input) {
        String lowerCase = input.toLowerCase(Locale.FRANCE);
        String decomposed = Normalizer.normalize(lowerCase, Normalizer.Form.NFD);
        return decomposed.replaceAll("\\p{M}", "");
    }
}
