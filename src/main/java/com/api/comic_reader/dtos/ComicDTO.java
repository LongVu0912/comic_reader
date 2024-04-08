package com.api.comic_reader.dtos;

import com.api.comic_reader.entities.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicDTO {
    private Long id;

    private String name;

    private String author;

    private Long view;

    private String description;

    private Boolean isFinished;

    private String imagePath;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicGenreEntity> comicGenres;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;
}
