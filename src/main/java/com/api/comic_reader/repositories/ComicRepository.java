package com.api.comic_reader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicEntity;

@Repository
public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
    @Modifying
    @Query("UPDATE ComicEntity c SET c.view = c.view + 1 WHERE c = :comicEntity")
    void increaseView(@Param("comicEntity") ComicEntity comicEntity);

    Optional<ComicEntity> findByName(String name);

    List<ComicEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM ComicEntity c JOIN c.genres g WHERE g.genre.id IN :genreIds")
    List<ComicEntity> findByGenresIdIn(@Param("genreIds") List<Long> genreIds);
}
