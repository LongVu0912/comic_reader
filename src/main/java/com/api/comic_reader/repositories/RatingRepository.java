package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.RatingEntity;
import com.api.comic_reader.entities.UserEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    // This method checks if a rating exists for a specific comic by a specific user.
    // It returns true if the rating exists, otherwise it returns false.
    boolean existsByComicAndUser(ComicEntity comic, UserEntity user);

    // This method updates the score of a rating for a specific comic by a specific user.
    // It does not return any value.
    @Modifying
    @Query("update RatingEntity r set r.score = :score where r.comic = :comic and r.user = :user")
    void updateByComicAndUser(
            @Param("comic") ComicEntity comic, @Param("user") UserEntity user, @Param("score") Long score);

    // This method calculates the average rating of a specific comic.
    // It returns the average rating as a Double.
    @Query("select avg(r.score) from RatingEntity r where r.comic = :comic")
    Double getComicAverageRating(@Param("comic") ComicEntity comic);

    // This method finds a rating for a specific comic by a specific user.
    // It returns the RatingEntity if found, otherwise it returns null.
    @Query("select r from RatingEntity r where r.comic = :comic and r.user = :user")
    RatingEntity findByComicAndUser(@Param("comic") ComicEntity comic, @Param("user") UserEntity user);
}
