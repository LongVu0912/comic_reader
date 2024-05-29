package com.api.comic_reader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ComicEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    // This method is used to find a chapter by its ID.
    // It returns an Optional that contains the ChapterEntity if found, otherwise it returns an empty Optional.
    @SuppressWarnings("null")
    Optional<ChapterEntity> findById(Long id);

    // This method is used to find all chapters associated with a specific comic.
    // It returns a list of ChapterEntity objects.
    List<ChapterEntity> findByComic(ComicEntity comic);

    // This method is used to find the most recently created chapter of a specific comic.
    // It returns an Optional that contains the ChapterEntity if found, otherwise it returns an empty Optional.
    Optional<ChapterEntity> findTopByComicOrderByCreatedAtDesc(ComicEntity comic);
}
