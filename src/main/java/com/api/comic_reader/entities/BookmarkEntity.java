package com.api.comic_reader.entities;

import jakarta.persistence.*;

import com.api.comic_reader.entities.composite_keys.BookmarkKey;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookmark")
public class BookmarkEntity {

    @EmbeddedId
    private BookmarkKey id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("comicId")
    @JoinColumn(name = "comic_id")
    private ComicEntity comic;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
