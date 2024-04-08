package com.api.comic_reader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @ManyToOne
    @JoinColumn(name = "comic_user_id")
    private ComicUserEntity comicUser;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Date createAt;

}
