package com.medilabo.frontendservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @Size(min = 3, message = "Username must be more than 3 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters")
    private String username;
    @Size(min = 3, message = "Password can't be empty and must be more than 3 characters")
    private String password;
    private String message;
    private boolean isAuthenticated = false;
}
