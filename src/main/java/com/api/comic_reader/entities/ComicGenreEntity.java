package com.api.comic_reader.entities;

import jakarta.persistence.*;

import com.api.comic_reader.entities.composite_keys.ComicGenreKey;

import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("comicId")
    @JoinColumn(name = "comic_id")
    private ComicEntity comic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
}
