package com.medilabo.frontendservice.service;

import com.medilabo.frontendservice.dto.PatientDTO;
import com.medilabo.frontendservice.proxy.PatientProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientProxy patientProxy;

    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public List<PatientDTO> getAllPatients() {
        return patientProxy.getAllPatients();
    }

    public PatientDTO getPatientById(Integer id) {
        return patientProxy.getPatientById(id);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        return patientProxy.createPatient(patientDTO);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO, Integer id) {
        return patientProxy.updatePatient(patientDTO, id);
    }

    public Boolean deletePatient(Integer id) {
        Boolean success = patientProxy.deletePatient(id);
        if (success == null) { return false; }
        return success;
    }
}
