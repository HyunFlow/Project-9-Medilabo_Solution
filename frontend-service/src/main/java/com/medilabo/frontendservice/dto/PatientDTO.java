package com.medilabo.frontendservice.dto;

import java.time.LocalDate;

public record PatientDTO(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String address,
        String zipCode,
        String phone,
        Integer height,
        Integer weight,
        Boolean smoker
) {
    public PatientDTO() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
}