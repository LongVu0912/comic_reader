package com.api.comic_reader.entities;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "chapter_image")
public class ChapterImageEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @JsonBackReference: This annotation is used to handle the back part of reference where the owner of the fields.
    // It will be serialized normally.

    // @ManyToOne: This annotation is used to create a many-to-one relationship between entities.

    // @JoinColumn: This annotation provides the name of the column in the database that holds the foreign key.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field. It can also
    // specify additional column properties such as name, nullable, and columnDefinition.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @Column(name = "image_order", nullable = false)
    private Long imageOrder;

    @Column(name = "image_data", columnDefinition = "BYTEA")
    private byte[] imageData;
}
