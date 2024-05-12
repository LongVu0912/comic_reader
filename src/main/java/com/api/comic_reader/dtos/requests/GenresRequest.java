package com.api.comic_reader.dtos.requests;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenresRequest {
    private List<Long> genreIds;
}
