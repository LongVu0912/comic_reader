package com.api.comic_reader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre_description", nullable = false)
    private String genreDescription;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComicGenreEntity> genreComics;
}
