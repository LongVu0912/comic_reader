package com.api.comic_reader.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeInformationRequest {
    private String fullName;

    private String dateOfBirth;

    private Boolean isMale;
}
