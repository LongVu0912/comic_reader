package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.ComicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ComicUserRepository extends JpaRepository<ComicUserEntity, Long> {
    ComicUserEntity findByEmail(String email);
    
}
