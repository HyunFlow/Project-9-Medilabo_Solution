package com.medilabo.patientservice.configuration;

import com.medilabo.patientservice.model.Gender;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository repository;

    public DataInitializer(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Deleting all patient information...");
        repository.deleteAll();

        Patient p1 = new Patient();
        p1.setFirstName("Test");
        p1.setLastName("TestNone");
        p1.setDateOfBirth(LocalDate.of(1966, 12, 31));
        p1.setGender(Gender.valueOf("F"));
        p1.setAddress("1 Brookside St");
        p1.setPhone("100-222-333");

        Patient p2 = new Patient();
        p2.setFirstName("Test");
        p2.setLastName("TestBorderline");
        p2.setDateOfBirth(LocalDate.of(1945, 6, 24));
        p2.setGender(Gender.valueOf("M"));
        p2.setAddress("2 High St");
        p2.setPhone("200-333-4444");

        Patient p3 = new Patient();
        p3.setFirstName("Test");
        p3.setLastName("TestInDanger");
        p3.setDateOfBirth(LocalDate.of(2004, 6, 18));
        p3.setGender(Gender.valueOf("M"));
        p3.setAddress("1 Brookside St");
        p3.setPhone("300-444-5555");

        Patient p4 = new Patient();
        p4.setFirstName("Test");
        p4.setLastName("TestEarlyOnset");
        p4.setDateOfBirth(LocalDate.of(2002, 6, 28));
        p4.setGender(Gender.valueOf("F"));
        p4.setAddress("1 Brookside St");
        p4.setPhone("400-555-6666");

        repository.save(p1);
        repository.save(p2);
        repository.save(p3);
        repository.save(p4);

        log.info("Test patient data initialized successfully.");
    }
}