package com.api.comic_reader.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicRequest {
    private String name;

    private String author;

    private String description;

    private MultipartFile thumbnailImage;
}
