package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookmarkResponse {
    private Long chapterId;
    private String chapterTitle;
    private Long chapterNumber;
}
