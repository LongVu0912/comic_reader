package com.api.comic_reader.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterRequest {
    private String title;

    private Long chapterNumber;

    private Long comicId;
}
