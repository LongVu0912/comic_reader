package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ComicUserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ComicUserRepository extends JpaRepository<ComicUserEntity, Long> {
    Optional<ComicUserEntity> findByEmail(String email);
    
    @SuppressWarnings("null")
    Optional<ComicUserEntity> findById(Long id);
}
