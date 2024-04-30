package com.api.comic_reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.BookmarkEntity;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkKey> {

    boolean existsByChapterAndComicUser(ChapterEntity chapter, UserEntity comicUser);

    List<BookmarkEntity> findByComicUser(UserEntity comicUser);
}
