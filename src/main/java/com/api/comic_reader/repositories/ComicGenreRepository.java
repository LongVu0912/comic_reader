package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.composite_keys.ComicGenreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ComicGenreRepository extends JpaRepository<ComicGenreEntity, ComicGenreKey> {
    
}
