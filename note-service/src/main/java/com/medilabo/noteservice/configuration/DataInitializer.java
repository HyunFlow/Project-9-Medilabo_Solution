package com.medilabo.noteservice.configuration;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final NoteRepository noteRepository;

    public DataInitializer(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Deleting all notes...");
        noteRepository.deleteAll();

        Note note1 = new Note();
        note1.setPadId(1);
        note1.setPatient("TestNone");
        note1.setNote("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");

        Note note2 = new Note();
        note2.setPadId(2);
        note2.setPatient("TestBorderline");
        note2.setNote("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");

        Note note3 = new Note();
        note3.setPadId(2);
        note3.setPatient("TestBorderline");
        note3.setNote("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois II remarque également que son audition continue d'être anormale");

        Note note4 = new Note();
        note4.setPadId(3);
        note4.setPatient("TestinDanger");
        note4.setNote("Le patient déclare qu'il fume depuis peu");

        Note note5 = new Note();
        note5.setPadId(3);
        note5.setPatient("TestinDanger");
        note5.setNote("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière II se plaint également de crises d'apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");

        Note note6 = new Note();
        note6.setPadId(4);
        note6.setPatient("TestEarlyOnset");
        note6.setNote("Le patient déclare qu'il lui est devenu difficile de monter les escaliers II se plaint également d'être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");

        Note note7 = new Note();
        note7.setPadId(4);
        note7.setPatient("TestEarlyOnset");
        note7.setNote("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");

        Note note8 = new Note();
        note8.setPadId(4);
        note8.setPatient("TestEarlyOnset");
        note8.setNote("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");

        Note note9 = new Note();
        note9.setPadId(4);
        note9.setPatient("TestEarlyOnset");
        note9.setNote("Taille, Poids, Cholestérol, Vertige et Réaction");

        List<Note> notes = List.of(note1, note2, note3, note4, note5, note6, note7, note8, note9);
        noteRepository.saveAll(notes);

        log.info("Test notes loaded successfully: {} notes", noteRepository.count());
    }
}
