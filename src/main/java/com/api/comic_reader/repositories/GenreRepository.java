package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    
}
