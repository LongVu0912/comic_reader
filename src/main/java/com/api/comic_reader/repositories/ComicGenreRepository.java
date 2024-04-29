package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.composite_keys.ComicGenreKey;

@Repository
public interface ComicGenreRepository extends JpaRepository<ComicGenreEntity, ComicGenreKey> {}
