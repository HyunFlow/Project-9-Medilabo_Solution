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

import static com.medilabo.frontendservice.constant.ErrorConstant.*;
import static com.medilabo.frontendservice.constant.MessageConstant.*;

@Slf4j
@RequestMapping("/notes")
@Controller
public class NoteController {
    private final NoteService  noteService;
    private final PatientService patientService;
    private final RiskService riskService;

    public NoteController(NoteService noteService, PatientService patientService, RiskService riskService) {
        this.noteService = noteService;
        this.patientService = patientService;
        this.riskService = riskService;
    }

    @GetMapping("/create")
    public String createNote(@RequestParam("patId") Integer patId, Model model) {
        PatientDTO patient = patientService.getPatientById(patId);
        if (patient == null) {
            log.error("Patient not found");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patientsList";
        }

        NoteDTO note = new NoteDTO();
        note.setPatId(patId);
        note.setPatient(patient.getFirstName() + " " + patient.getLastName());
        note.setContent("");

        model.addAttribute("note", note);
        return "noteCreate";
    }

    @PostMapping("/create")
    public String createNote(@Valid @ModelAttribute("note") NoteDTO note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("note creation form has incorrect entries");
            return "noteCreate";
        }

        NoteDTO newNote = noteService.createNote(note);
        if (newNote == null) {
            log.error("New note is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_CREATE);
            model.addAttribute("note", note);
            return "noteCreate";
        }

        Integer patId = note.getPatId();
        PatientDTO patient = patientService.getPatientById(patId);
        if (patient == null) {
            log.error("Patient ID {} is null", patId);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patientsList";
        }

        String risk = riskService.getRiskLevelForPatientId(patId);
        if (risk == null) {
            log.error("Risk level for Patient ID {} is null", patId);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        } else {
            model.addAttribute("risk", risk);
        }

        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_CREATE);
        model.addAttribute("patient",  patient);
        model.addAttribute("notes",  noteService.getAllNotesByPatId(patId));

        return "patientView";
    }

    @GetMapping("/update")
    public String updateNote(@RequestParam("id") String id, Model model) {
        log.info("Display note ID {}", id);
        model.addAttribute("note", noteService.getNoteById(id));
        return "noteUpdate";
    }

    @PutMapping("/update")
    public String  updateNote(@Valid @ModelAttribute("note") NoteDTO note, BindingResult result, Model model) {

        String id = note.getId();

        if (result.hasErrors()) {
            log.warn("note update form has incorrect entries");
            return "noteUpdate";
        }

        NoteDTO modifiedNote = noteService.updateNote(note, id);
        Integer patId;

        if (modifiedNote == null) {
            log.error("Note ID {} is null", id);
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_UPDATE);
            patId = note.getPatId();
        } else {
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_UPDATE);
            patId = modifiedNote.getPatId();
        }

        String risk = riskService.getRiskLevelForPatientId(patId);
        if (risk == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        } else {
            model.addAttribute("risk", risk);
        }

        model.addAttribute("patient",  patientService.getPatientById(patId));
        model.addAttribute("notes",  noteService.getAllNotesByPatId(patId));
        return "patientView";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") String id, @RequestParam("patId") Integer patId, Model model) {
        if (noteService.deleteNote(id)) {
            log.info("Note ID {} is deleted", id);
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_DELETE);
        }
        else {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_DELETE);
        }
        model.addAttribute("patient", patientService.getPatientById(patId));
        model.addAttribute("notes", noteService.getAllNotesByPatId(patId));
        return "patientView";
    }

}
