package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    
}
