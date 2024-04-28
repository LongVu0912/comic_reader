package com.api.comic_reader.dtos.requests;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterRequest {
    private String title;

    @Min(value = 0, message = "Chapter number must be equal or greater than 0")
    private Long chapterNumber;

    private Long comicId;
}
