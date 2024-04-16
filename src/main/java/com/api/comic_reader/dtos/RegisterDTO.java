package com.api.comic_reader.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullName")
    private String fullName;
    
    @JsonProperty("email")
    private String email;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

    @JsonProperty("isMale")
    private Boolean isMale;
}
