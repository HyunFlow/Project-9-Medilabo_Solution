package com.medilabo.frontendservice.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class PatientDTO{
        Integer id;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        String gender;
        String address;
        String zipCode;
        String phone;
        Integer height;
        Integer weight;
        Boolean smoker;
}