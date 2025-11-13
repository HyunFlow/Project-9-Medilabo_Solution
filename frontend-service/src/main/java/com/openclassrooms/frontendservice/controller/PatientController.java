package com.openclassrooms.frontendservice.controller;
import com.openclassrooms.frontendservice.dto.PatientDTO;
import com.openclassrooms.frontendservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openclassrooms.frontendservice.constant.ErrorConstant.*;
import static com.openclassrooms.frontendservice.constant.MessageConstant.*;

@Controller
@Slf4j
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/view")
    public String patientView(@RequestParam Integer id, Model model) {
        PatientDTO patient = patientService.getPatientById(id);

        if (patient == null) {
            log.warn("Patient not found for id: "+ id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patientsList";
        }
        model.addAttribute("patient", patient);
        return "patientView";
    }

    @GetMapping
    public String getAllPatients(Model model) {
        List<PatientDTO> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patientsList";
    }

    @GetMapping("/update")
    public String updatePatient(@RequestParam("id") Integer id, Model model) {
        PatientDTO patient = patientService.getPatientById(id);

        if (patient == null) {
            log.warn("Patient not found for id: "+ id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patientsList";
        }
        model.addAttribute("patient", patient);
        return "patientUpdate";
    }

    @PutMapping("/update")
    public String updatePatient(@Valid @ModelAttribute("patient") PatientDTO patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Patient modification form has incorrect entries");
            return "patientUpdate";
        }

        PatientDTO modifiedPatient = patientService.updatePatient(patient, patient.id());

        if (modifiedPatient == null) {
            log.warn("Patient update failed for id: "+ patient.id());
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_UPDATE);
            return "patientUpdate";
        }
        return "redirect:/patients";
    }

    @GetMapping("/create")
    public String createPatient(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patientCreate";
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient")  PatientDTO patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Patient creation form has incorrect entries");
            return "patientCreate";
        }

        PatientDTO createdPatient = patientService.createPatient(patient);

        if (createdPatient == null) {
            log.warn("Patient creation failed");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_CREATE);
            return "patientCreate";
        }

        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_CREATE);
        return "redirect:/patients/view?id=" + createdPatient.id();
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam("id") Integer id, Model model) {

        Boolean success = patientService.deletePatient(id);

        if(success == null || !success) {
            log.warn("Patient deletion failed for id: " + id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_DELETE);
        }
        return "redirect:/patients";
    }
}
