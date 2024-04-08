package com.api.comic_reader.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comic_user")
public class ComicUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "is_male", nullable = false)
    private Boolean isMale;

    @Column(name = "is_banned", nullable = false, columnDefinition = "boolean default false")
    private Boolean isBanned;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "bigint default 1")
    private RoleEntity role;

    @OneToMany(mappedBy = "comicUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "comicUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarks;
}
