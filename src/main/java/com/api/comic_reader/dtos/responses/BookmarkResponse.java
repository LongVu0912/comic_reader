package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookmarkResponse {
    private Long comicId;
    private String name;
    private String thumbnailUrl;
    private ChapterResponse lastChapter;
}
