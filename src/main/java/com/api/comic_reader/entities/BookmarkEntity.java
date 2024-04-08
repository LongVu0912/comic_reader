package com.api.comic_reader.entities;

import com.api.comic_reader.entities.composite_keys.BookmarkKey;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("chapterId")
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("comicUserId")
    @JoinColumn(name = "comic_user_id")
    private ComicUserEntity comicUser;
}
