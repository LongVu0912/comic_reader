package com.api.comic_reader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
    // This method increases the view count of a specific comic.
    // It does not return any value.
    @Modifying
    @Query("UPDATE ComicEntity c SET c.view = c.view + 1 WHERE c = :comicEntity")
    void increaseView(@Param("comicEntity") ComicEntity comicEntity);

    // This method finds a comic by its name.
    // It returns an Optional that contains the ComicEntity if found, otherwise it returns an empty Optional.
    Optional<ComicEntity> findByName(String name);

    // This method finds all comics that contain a specific string in their name, ignoring case.
    // It returns a list of ComicEntity objects.
    List<ComicEntity> findByNameContainingIgnoreCase(String name);

    // This method finds all comics that have all of the specified genres.
    // It returns a list of ComicEntity objects.
    @Query(
            "SELECT c FROM ComicEntity c JOIN c.genres g WHERE g.genre.id IN :genreIds GROUP BY c HAVING COUNT(DISTINCT g.genre.id) = :genreCount")
    List<ComicEntity> findByGenresIdIn(@Param("genreIds") List<Long> genreIds, @Param("genreCount") long genreCount);

    // This method finds all comics that have a specific genre.
    // It returns a list of ComicEntity objects.
    @Query("SELECT c FROM ComicEntity c JOIN c.genres g WHERE g.genre.id = :genreId")
    List<ComicEntity> findByGenreId(Long genreId);
}
