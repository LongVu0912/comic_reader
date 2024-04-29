package com.api.comic_reader.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private Long userId;
    private String fullName;
    private String content;
    private String createdAt;
}