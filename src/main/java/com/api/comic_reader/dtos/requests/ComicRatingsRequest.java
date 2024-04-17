package com.api.comic_reader.dtos.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComicRatingsDTO {

    @JsonProperty("comicId")
    private Long comicId;

    @JsonProperty("comicUserId")
    private Long comicUserId;

    @JsonProperty("score")
    private Integer score;

}
