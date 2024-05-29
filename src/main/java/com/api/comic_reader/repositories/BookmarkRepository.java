package com.api.comic_reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.BookmarkEntity;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkKey> {
    // This method checks if a bookmark exists for a specific comic by a specific user.
    // It returns true if the bookmark exists, otherwise it returns false.
    boolean existsByComicAndUser(ComicEntity comic, UserEntity user);

    // This method deletes a bookmark for a specific comic by a specific user.
    // It does not return any value.
    void deleteByComicAndUser(ComicEntity comic, UserEntity user);

    // This method finds all bookmarks associated with a specific user.
    // It returns a list of BookmarkEntity objects.
    List<BookmarkEntity> findByUser(UserEntity user);

    // This method deletes all bookmarks associated with a specific comic.
    // It does not return any value.
    void deleteByComic(ComicEntity comic);
}
