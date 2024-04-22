package com.api.comic_reader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @JsonManagedReference
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChapterImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private ComicEntity comic;

    @JsonManagedReference
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarked_by;

    @JsonManagedReference
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
