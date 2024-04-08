package com.api.comic_reader.entities.composite_keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


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
