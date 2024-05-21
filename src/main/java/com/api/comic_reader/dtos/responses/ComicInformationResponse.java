package com.api.comic_reader.dtos.responses;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ComicInformationResponse {
    private Long id;
    private String name;
    private String author;
    private String description;
    private String thumbnailUrl;
    private Long view;
    private boolean isFinished;
    private Long averageRatingScore;
    private Long userRatingScore;
    private List<ComicGenreResponse> genres;
}
