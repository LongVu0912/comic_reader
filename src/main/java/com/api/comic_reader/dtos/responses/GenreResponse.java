package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GenreResponse {
    private Long id;
    private String name;
    private String genreDescription;
}
