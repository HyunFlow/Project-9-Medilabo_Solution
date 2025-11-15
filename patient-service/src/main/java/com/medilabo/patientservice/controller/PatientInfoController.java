package com.medilabo.patientservice.controller;

import com.medilabo.patientservice.dto.PatientDTO;
import com.medilabo.patientservice.service.PatientInfoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/patients")
public class PatientInfoController {

    @Autowired
    private PatientInfoService patientInfoService;

    public PatientInfoController(PatientInfoService patientInfoService) {
        this.patientInfoService = patientInfoService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patientDTOs = patientInfoService.getAllPatients();
        if (patientDTOs.isEmpty()) {
            log.info("No patient data found (list is empty)");
        } else {
            log.info("Successfully retrieved {} patient information records", patientDTOs.size());
        }

        return ResponseEntity.status(HttpStatus.OK).body(patientDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Integer id) {
        PatientDTO patient = patientInfoService.getPatientById(id);
        if (patient == null) {
            log.info("Patient not found (ID: {})", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            log.info("Successfully retrieved patient information record (ID: {})", patient.id());
            return ResponseEntity.status(HttpStatus.OK).body(patient);
        }
    }

    @PostMapping
    public ResponseEntity<PatientDTO> addNewPatient(@RequestBody PatientDTO req) {
        PatientDTO result = patientInfoService.addPatient(req);
        if (result != null) {
            log.info("Successfully created patient (ID: {})", result.id());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable("id") Integer id,
        @RequestBody PatientDTO req) {
        PatientDTO result = patientInfoService.updatePatient(id, req);
        if (result != null) {
            log.info("Successfully updated patient information (ID: {})", result.id());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatient(@PathVariable("id") Integer id) {
        Boolean result = patientInfoService.deletePatient(id);
        if (result) {
            log.info("Successfully deleted patient (ID: {})", id);
        } else {
            log.warn("Failed to delete patient (ID: {}).", id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
