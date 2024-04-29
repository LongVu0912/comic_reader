package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.CommentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByChapter(ChapterEntity chapter);
}
