package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserInformationResponse {

    private Long id;

    private String email;

    private String fullName;

    private String dateOfBirth;

    private boolean isMale;
}
