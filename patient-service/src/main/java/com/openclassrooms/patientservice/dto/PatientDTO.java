package com.openclassrooms.patientservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.patientservice.model.Gender;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public record PatientDTO(
    Integer id,
    @NotBlank
    String firstName,
    @NotBlank
    String lastName,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth,
    Gender gender,
    String address,
    String zipCode,
    String phone,
    Integer height,
    Integer weight,
    Boolean smoker
) {

}