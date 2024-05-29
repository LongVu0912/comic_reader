package com.api.comic_reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.CommentEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // This method is used to find all comments associated with a specific chapter.
    // It returns a list of CommentEntity objects.
    List<CommentEntity> findByChapter(ChapterEntity chapter);
}
