package com.api.comic_reader.entities;


import com.api.comic_reader.entities.composite_keys.ComicGenreKey;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comic_genre")
public class ComicGenreEntity {

    @EmbeddedId
    private ComicGenreKey id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("comicId")
    @JoinColumn(name = "comic_id")
    private ComicEntity comic;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

}
