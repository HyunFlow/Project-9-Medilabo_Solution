package com.openclassrooms.patientservice.service;

import com.openclassrooms.patientservice.dto.PatientDTO;
import com.openclassrooms.patientservice.model.Patient;
import com.openclassrooms.patientservice.repository.PatientRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientInfoService {

    @Autowired
    private final PatientRepository patientRepository;

    public PatientInfoService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public PatientDTO getPatientById(Integer id) {
        Patient patientData = patientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        return patientMapper(patientData);
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::patientMapper).toList();
    }

    @Transactional
    public PatientDTO addPatient(PatientDTO dto) {
        Patient newPatient = new Patient();
        newPatient.setFirstName(dto.firstName());
        newPatient.setLastName(dto.lastName());
        newPatient.setDateOfBirth(dto.dateOfBirth());
        newPatient.setGender(dto.gender());
        newPatient.setAddress(dto.address());
        newPatient.setPhone(dto.phone());
        newPatient.setHeight(dto.height());
        newPatient.setWeight(dto.weight());
        newPatient.setSmoker(dto.smoker());

        return patientMapper(patientRepository.save(newPatient));
    }

    @Transactional
    public PatientDTO updatePatient(Integer id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient ID: " + id + " not found"));
        patient.setFirstName(dto.firstName());
        patient.setLastName(dto.lastName());
        patient.setAddress(dto.address());
        patient.setPhone(dto.phone());

        return patientMapper(patientRepository.save(patient));
    }

    @Transactional
    public boolean deletePatient(Integer id) {
        if (!patientRepository.existsById(id)) {
            return false;
        }

        patientRepository.deleteById(id);
        return !patientRepository.existsById(id);
    }

    public PatientDTO patientMapper(Patient patient) {
        return new PatientDTO(
            patient.getId(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getDateOfBirth(),
            patient.getGender(),
            patient.getAddress(),
            patient.getZipCode(),
            patient.getPhone(),
            patient.getHeight(),
            patient.getWeight(),
            patient.getSmoker()
        );
    }
}
