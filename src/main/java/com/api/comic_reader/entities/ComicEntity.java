package com.api.comic_reader.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comic")
public class ComicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "view", nullable = false, columnDefinition = "bigint default 0")
    private Long view;

    @Column(name = "description", nullable = false)
    private String description;
    
    @Lob
    @Column(name = "thumbnail_image")
    private byte[] thumbnailImage;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChapterEntity> chapters;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicGenreEntity> comicGenres;

    @JsonManagedReference
    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;
}
