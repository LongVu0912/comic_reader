package com.api.comic_reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ChapterImageEntity;

@Repository
public interface ChapterImageRepository extends JpaRepository<ChapterImageEntity, Long> {
    @Query("SELECT MAX(imageOrder) FROM ChapterImageEntity c WHERE c.chapter.id = :chapterId")
    Long findMaxImageOrder(Long chapterId);

    List<ChapterImageEntity> findByChapter(ChapterEntity chapter);
}
