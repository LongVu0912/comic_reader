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
@Table(name = "comic")
public class ComicEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field. It can also
    // specify additional column properties such as name, nullable, unique, length, and columnDefinition.

    // @OneToMany: This annotation is used to create a one-to-many relationship between entities. It is always the
    // owning side of the relationship.

    // @ManyToOne: This annotation is used to create a many-to-one relationship between entities.
    // @JoinColumn: This annotation provides the name of the column in the database that holds the foreign key.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "view", nullable = false, columnDefinition = "bigint default 0")
    private Long view;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "thumbnail_image", columnDefinition = "BYTEA")
    private byte[] thumbnailImage;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChapterEntity> chapters;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicGenreEntity> genres;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarkedBy;
}
