package com.medilabo.frontendservice.controller;
import com.medilabo.frontendservice.dto.NoteDTO;
import com.medilabo.frontendservice.dto.PatientDTO;
import com.medilabo.frontendservice.service.NoteService;
import com.medilabo.frontendservice.service.PatientService;
import com.medilabo.frontendservice.service.RiskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.medilabo.frontendservice.constant.ErrorConstant.*;
import static com.medilabo.frontendservice.constant.MessageConstant.*;

@Controller
@Slf4j
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final NoteService noteService;
    private final RiskService riskService;

    public PatientController(PatientService patientService, NoteService noteService,  RiskService riskService) {
        this.patientService = patientService;
        this.noteService = noteService;
        this.riskService = riskService;
    }

    @GetMapping("/view")
    public String patientView(@RequestParam Integer id, Model model) {
        PatientDTO patient = patientService.getPatientById(id);

        if (patient == null) {
            log.warn("Patient not found for id: {}", id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patientsList";
        }
        model.addAttribute("patient", patient);

        String risk = riskService.getRiskLevelForPatientId(patient.getId());
        if (risk == null) {
            log.warn("Risk Patient id {} is null",id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        } else {
            model.addAttribute("risk", risk);
        }

        List<NoteDTO> notes = noteService.getAllNotesByPatId(patient.getId());
        if (notes == null) {
            log.warn("Notes not found for id: {}", id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_NOT_FOUND);
        } else {
            model.addAttribute("notes", notes);
        }
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
            log.warn("Patient not found for id: {}", id);
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

        PatientDTO modifiedPatient = patientService.updatePatient(patient, patient.getId());

        if (modifiedPatient == null) {
            log.warn("Patient update failed for id: {}", patient.getId());
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_UPDATE);
            return "patientUpdate";
        }

        Integer patId = patient.getId();
        List<NoteDTO> notes = noteService.getAllNotesByPatId(patId);
        if (notes == null) {
            log.warn("Notes not found for Patient id: {}", patient.getId());
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_NOT_FOUND);
            model.addAttribute("risk", UNAVAILABLE);
        } else {
            model.addAttribute("notes", notes);
            String risk = riskService.getRiskLevelForPatientId(patId);
            if (risk == null) {
                model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
                model.addAttribute("risk", UNAVAILABLE);
            } else {
                model.addAttribute("risk", risk);
            }
        }
        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_UPDATE);
        model.addAttribute("patient", modifiedPatient);
        return "patientView";
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
        model.addAttribute("patient", createdPatient);
        model.addAttribute("risk", "None");
        return "patientView";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam("id") Integer id, Model model) {

        Boolean success = patientService.deletePatient(id);

        if(success == null || !success) {
            log.warn("Patient deletion failed for id: {}", id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_DELETE);
        }
        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_DELETE);
        model.addAttribute("patients", patientService.getAllPatients());
        return "patientsList";
    }
}
