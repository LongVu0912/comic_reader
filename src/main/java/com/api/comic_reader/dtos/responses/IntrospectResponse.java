package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectResponse {
    private boolean valid;
}
