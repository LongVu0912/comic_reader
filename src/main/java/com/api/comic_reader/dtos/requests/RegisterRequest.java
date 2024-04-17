package com.api.comic_reader.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;

    private String password;

    private String fullName;
    
    private String email;

    private String dateOfBirth;

    private Boolean isMale;
}
