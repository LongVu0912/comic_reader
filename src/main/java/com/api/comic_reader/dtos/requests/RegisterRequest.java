package com.api.comic_reader.dtos.requests;

import jakarta.validation.constraints.Pattern;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username must contain only lowercase letters and numbers")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    private String password;

    private String fullName;

    private String dateOfBirth;

    private Boolean isMale;
}
