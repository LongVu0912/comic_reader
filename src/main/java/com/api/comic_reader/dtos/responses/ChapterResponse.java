package com.api.comic_reader.dtos.responses;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChapterResponse {
    private Long id;
    private String title;
    private Long chapterNumber;
    private String createdAt;
    List<String> imageUrls;
}
