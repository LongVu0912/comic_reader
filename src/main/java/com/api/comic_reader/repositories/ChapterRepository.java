package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ComicEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    @SuppressWarnings("null")
    Optional<ChapterEntity> findById(Long id);
    List<ChapterEntity> findByComic(ComicEntity comic);
    Optional<ChapterEntity> findTopByComicOrderByCreatedAtDesc(ComicEntity comic);
}
