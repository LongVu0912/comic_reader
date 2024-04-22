package com.api.comic_reader.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterImageRequest {
    private Long chapterId;
    private MultipartFile imageData;
}
