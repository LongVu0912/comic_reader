package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginResponse {
    private Long id;

    private String token;

    private boolean authenticated;
}
