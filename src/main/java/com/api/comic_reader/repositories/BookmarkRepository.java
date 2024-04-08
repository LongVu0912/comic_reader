package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.BookmarkEntity;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkKey> {
    
}
