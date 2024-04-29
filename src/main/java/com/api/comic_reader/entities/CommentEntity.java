package com.api.comic_reader.entities;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

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
    private UserEntity comicUser;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;
}
