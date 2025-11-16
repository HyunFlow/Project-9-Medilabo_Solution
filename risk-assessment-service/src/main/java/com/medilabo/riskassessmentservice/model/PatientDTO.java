package com.medilabo.riskassessmentservice.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
public class PatientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private List<String> notes;

    public Integer getAge() {
        if (dateOfBirth == null) {
            return -1;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    public char getGenderChar() {
        if (gender == null) {
            return 'x';
        }
        char chr = gender.charAt(0);
        if (chr != 'F' && chr != 'M') {
            return 'x';
        }
        return chr;
    }

    // test helper
    public void setAgeGender(Integer age, String gender) {
        this.dateOfBirth = LocalDate.now().minus(Period.ofYears(age));
        this.gender = gender;
    }
}
