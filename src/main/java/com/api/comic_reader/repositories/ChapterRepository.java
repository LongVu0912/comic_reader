package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ComicEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository

public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    List<ChapterEntity> findByComic(ComicEntity comic);
}
