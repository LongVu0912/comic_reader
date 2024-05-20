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
    private ChapterResponse lastChapter;
    private boolean isFinished;
    private Long ratingScore;
    private List<ComicGenreResponse> genres;
}
