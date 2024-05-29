package com.api.comic_reader.entities;

import java.util.Date;

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
@Table(name = "comment")
public class CommentEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @ManyToOne: This annotation is used to create a many-to-one relationship between entities.

    // @JoinColumn: This annotation provides the name of the column in the database that holds the foreign key.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;
}
