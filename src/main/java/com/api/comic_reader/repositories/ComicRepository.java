package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
    
}
