package com.api.comic_reader.entities.composite_keys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ComicGenreKey implements Serializable {

    private Long comicId;

    private Long genreId;
}
