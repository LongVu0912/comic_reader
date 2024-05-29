package com.api.comic_reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ChapterImageEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface ChapterImageRepository extends JpaRepository<ChapterImageEntity, Long> {
    // This method is used to find the maximum image order in a specific chapter.
    // It returns the maximum image order as a Long.
    @Query("SELECT MAX(imageOrder) FROM ChapterImageEntity c WHERE c.chapter.id = :chapterId")
    Long findMaxImageOrder(Long chapterId);

    // This method is used to find all images associated with a specific chapter.
    // It returns a list of ChapterImageEntity objects.
    List<ChapterImageEntity> findByChapter(ChapterEntity chapter);
}
