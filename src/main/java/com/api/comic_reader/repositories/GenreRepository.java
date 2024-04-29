package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {}
