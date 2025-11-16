package com.medilabo.riskassessmentservice.service;

import com.medilabo.riskassessmentservice.model.NoteDTO;
import com.medilabo.riskassessmentservice.model.PatientDTO;
import com.medilabo.riskassessmentservice.model.TriggerEnumeration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import static com.medilabo.riskassessmentservice.model.RiskLevelEnumeration.*;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final NoteService noteService;
    private final PatientService patientService;

    public String riskEvaluation(Integer patId) {
        // Retrieve information from sub-applications
        PatientDTO patient = patientService.getPatientId(patId);
        List<NoteDTO> notes = noteService.getNotesByPatId(patId);

        // verify integrity of the required information
        if (patient == null || notes == null) {
            return null;
        }
        int age = patient.getAge();
        if (age == -1) {
            return null;
        }
        char gender = patient.getGenderChar();
        if (gender != 'M' && gender != 'F') {
            return null;
        }

        // read notes and count triggers
        int triggerCount = triggerCount(notes);

        // Apply logics to define the Risk level
        if (triggerCount == 0) {
            return NONE;
        }
        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return BORDERLINE;
        }
        if (gender == 'M' && age < 30 && triggerCount == 3) {
            return IN_DANGER;
        }
        if (gender == 'F' && age < 30 && triggerCount == 4) {
            return IN_DANGER;
        }
        if (age > 30 && (triggerCount == 6 || triggerCount == 7)) {
            return IN_DANGER;
        }
        if (gender == 'M' && age < 30 && triggerCount >= 5) {
            return EARLY_ONSET;
        }
        if (gender == 'F' && age < 30 && triggerCount >= 7) {
            return EARLY_ONSET;
        }
        if (age > 30 && triggerCount >= 8) {
            return EARLY_ONSET;
        }
        return UNDEFINED;
    }

    public int triggerCount(List<NoteDTO> notes) {
        int count = 0;
        for (NoteDTO note : notes) {
            String normalizedNote = normalizer(note.getContent());

            for (TriggerEnumeration trigger : TriggerEnumeration.values()) {
                // set both notes and triggers to lower cases to avoid any miss-matches
                String normalizedTrigger = normalizer(trigger.getValue());

                if (normalizedNote.contains(normalizedTrigger)) {
                    count++;
                }
            }
        }
        return count;
    }

    public String normalizer(String input) {
        if (input == null) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        return normalized.toLowerCase();
    }
}
