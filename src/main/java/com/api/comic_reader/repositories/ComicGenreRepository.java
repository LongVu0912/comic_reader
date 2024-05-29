package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.composite_keys.ComicGenreKey;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface ComicGenreRepository extends JpaRepository<ComicGenreEntity, ComicGenreKey> {
    // This interface extends JpaRepository which provides methods to perform CRUD operations on ComicGenreEntity.
    // The primary key for ComicGenreEntity is a composite key (ComicGenreKey), which is used as the second generic
    // parameter for JpaRepository.
    // No additional methods are declared here, so all operations will use the standard methods provided by
    // JpaRepository.
}
