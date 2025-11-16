package com.medilabo.riskassessmentservice.service;

import com.medilabo.riskassessmentservice.model.NoteDTO;
import com.medilabo.riskassessmentservice.model.PatientDTO;
import com.medilabo.riskassessmentservice.model.RiskLevel;
import com.medilabo.riskassessmentservice.model.RiskResultDTO;
import com.medilabo.riskassessmentservice.proxy.NoteProxy;
import com.medilabo.riskassessmentservice.proxy.PatientProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final PatientProxy patientProxy;
    private final NoteProxy noteProxy;
    private final TriggerProvider triggerProvider;

    public RiskResultDTO assess(Integer patId) {
        PatientDTO patient = patientProxy.getPatientById(patId);
        if (patient == null) {
            return new RiskResultDTO(patId, null, null, -1, 0, RiskLevel.UNDEFINED);
        }
        int age = patient.getAge();
        char gender = patient.getGenderChar();

        if (age < 0 || gender != 'M' && gender != 'F') {
            return new RiskResultDTO(patId, patient.getFirstName(), patient.getLastName(), age, 0, RiskLevel.UNDEFINED);
        }

        List<NoteDTO> notes = noteProxy.getNotesByPatId(patId);

        String allText = "";
        if (notes != null && !notes.isEmpty()) {
            allText = notes.stream()
                    .map(NoteDTO::getContent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
        }

        int triggerCount = triggerProvider.countTriggers(allText);
        RiskLevel level = evaluateRisk(age, gender, triggerCount);

        return new RiskResultDTO(patId, patient.getFirstName(), patient.getLastName(), age, triggerCount, level);
    }

    private RiskLevel evaluateRisk(int age, char gender, int triggerCount) {
        boolean male = (gender == 'M');
        boolean female = (gender == 'F');

        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        if (age >= 30) {
            if (triggerCount >= 2 && triggerCount <= 5) {
                return RiskLevel.BORDERLINE;
            } else if (triggerCount >= 6 && triggerCount <= 7) {
                return RiskLevel.IN_DANGER;
            } else if (triggerCount >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
            return RiskLevel.NONE;
        }

        if (male) {
            if (triggerCount >= 3 && triggerCount < 5) {
                return RiskLevel.IN_DANGER;
            } else if (triggerCount >= 5) {
                return RiskLevel.EARLY_ONSET;
            }
            return RiskLevel.NONE;
        }

        if (female) {
            if (triggerCount >= 4 && triggerCount < 7) {
                return RiskLevel.IN_DANGER;
            } else if (triggerCount >= 7) {
                return RiskLevel.EARLY_ONSET;
            }
            return RiskLevel.NONE;
        }
        return RiskLevel.UNDEFINED;
    }

}
