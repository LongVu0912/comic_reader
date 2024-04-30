package com.api.comic_reader.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingRequest {
    private Long comicId;

    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5")
    private Long score;
}
