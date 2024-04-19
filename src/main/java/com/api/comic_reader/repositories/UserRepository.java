package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    
    @SuppressWarnings("null")
    Optional<UserEntity> findById(Long id);
}
