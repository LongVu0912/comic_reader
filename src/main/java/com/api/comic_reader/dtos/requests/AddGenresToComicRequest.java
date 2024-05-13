package com.api.comic_reader.dtos.requests;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddGenresToComicRequest {
    private Long comicId;
    private List<Long> genreIds;
}
