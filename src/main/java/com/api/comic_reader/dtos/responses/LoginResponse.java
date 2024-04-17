package com.api.comic_reader.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("username")
    private String username;

    @JsonProperty("jwt")
    private String jwt;

}
