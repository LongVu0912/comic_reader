package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.BookmarkEntity;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkKey> {

    // boolean existsByChapterAndUser(ChapterEntity chapter, UserEntity user);

    // List<BookmarkEntity> findByUser(UserEntity user);
}
