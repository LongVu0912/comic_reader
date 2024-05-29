package com.api.comic_reader.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.api.comic_reader.enums.Role;
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
@Table(name = "comic_user")
public class UserEntity {
    // @Id: This annotation is used to specify the primary key of an entity.

    // @GeneratedValue: This annotation provides for the specification of generation strategies for the values of
    // primary keys.

    // @Column: This annotation is used to specify the mapped column for a persistent property or field. It can also
    // specify additional column properties such as name, nullable, and unique.

    // @Enumerated: This annotation is used to specify that a persistent property or field should be persisted as a
    // enumerated type.

    // @JsonManagedReference: This annotation is used to handle the forward part of reference – the one that gets
    // serialized normally.

    // @OneToMany: This annotation is used to create a one-to-many relationship between the UserEntity and
    // RatingEntity/BookmarkEntity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "is_male", nullable = false)
    private Boolean isMale;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarks;
}
