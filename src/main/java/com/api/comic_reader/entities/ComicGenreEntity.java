package com.api.comic_reader.entities;

import jakarta.persistence.*;

import com.api.comic_reader.entities.composite_keys.ComicGenreKey;

import lombok.*;

// @Getter: This annotation is used to generate getters for all fields in the class.
// @Setter: This annotation is used to generate setters for all fields in the class.
// @Entity: This annotation specifies that the class is an entity and is mapped to a database table.
// @Builder: This annotation produces complex builder APIs.
// @NoArgsConstructor: This annotation generates a constructor with no parameters.
// @AllArgsConstructor: This annotation generates a constructor with one parameter for each field.
// @Table: This annotation specifies the name of the database table to be used for mapping.
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comic_genre")
public class ComicGenreEntity {
    // @EmbeddedId: This annotation is used to specify a composite primary key class that is an embeddable class.

    // @ManyToOne: This annotation is used to create a many-to-one relationship between entities.

    // @MapsId: This annotation is used to specify the mapped entity's attribute that corresponds to the primary key of
    // the entity.

    // @JoinColumn: This annotation provides the name of the column in the database that holds the foreign key.
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
