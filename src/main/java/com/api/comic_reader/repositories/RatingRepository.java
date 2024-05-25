package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.RatingEntity;
import com.api.comic_reader.entities.UserEntity;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    boolean existsByComicAndUser(ComicEntity comic, UserEntity user);

    @Modifying
    @Query("update RatingEntity r set r.score = :score where r.comic = :comic and r.user = :user")
    void updateByComicAndUser(
            @Param("comic") ComicEntity comic, @Param("user") UserEntity user, @Param("score") Long score);

    @Query("select avg(r.score) from RatingEntity r where r.comic = :comic")
    Double getComicAverageRating(@Param("comic") ComicEntity comic);

    @Query("select r from RatingEntity r where r.comic = :comic and r.user = :user")
    RatingEntity findByComicAndUser(@Param("comic") ComicEntity comic, @Param("user") UserEntity user);
}
