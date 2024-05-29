package com.api.comic_reader.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "chapter")
public class ChapterEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field. It can also
    // specify additional column properties such as name, nullable, and unique.

    // @JsonManagedReference: This annotation is used to handle the forward part of reference – the one that gets
    // serialized normally.

    // @OneToMany: This annotation is used to create a one-to-many relationship between entities. It is always the
    // owning side of the relationship.

    // @ManyToOne: This annotation is used to create a many-to-one relationship between entities.

    // @JoinColumn: This annotation provides the name of the column in the database that holds the foreign key.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chapter_number", nullable = false)
    private Long chapterNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChapterImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private ComicEntity comic;

    @JsonManagedReference
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
