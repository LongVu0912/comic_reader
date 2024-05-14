package com.api.comic_reader.dtos.requests;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicsAskGptRequest {
    private String name;
    private String description;
    private List<String> genres;
}
