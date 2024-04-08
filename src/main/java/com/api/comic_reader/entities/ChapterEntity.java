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
@Table(name = "chapter")
public class ChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chapter_number", nullable = false)
    private Long chapterNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "page_quantity", nullable = false)
    private Long pageQuantity;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private ComicEntity comic;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarked_by;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
