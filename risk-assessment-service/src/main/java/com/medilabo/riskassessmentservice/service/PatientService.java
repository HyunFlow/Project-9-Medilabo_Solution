package com.medilabo.riskassessmentservice.service;

import com.medilabo.riskassessmentservice.model.PatientDTO;
import com.medilabo.riskassessmentservice.proxy.PatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PatientService {
    private final PatientProxy patientProxy;
    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }
    public PatientDTO getPatientId(Integer patId) {
        return patientProxy.getPatientById(patId);
    }
}
