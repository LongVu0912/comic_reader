package com.api.comic_reader.entities;

import java.util.List;

import jakarta.persistence.*;

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
@Table(name = "genre")
public class GenreEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field. It can also
    // specify additional column properties such as name, nullable, and unique.

    // @OneToMany: This annotation is used to create a one-to-many relationship between entities. It is always the
    // owning side of the relationship.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "genre_description", nullable = false)
    private String genreDescription;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicGenreEntity> genreComics;
}
