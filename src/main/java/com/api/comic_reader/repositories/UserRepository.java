package com.api.comic_reader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.UserEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // This method is used to find a user by their username.
    // It returns an Optional that contains the UserEntity if found, otherwise it returns an empty Optional.
    Optional<UserEntity> findByUsername(String username);

    // This method is used to find a user by their email.
    // It returns an Optional that contains the UserEntity if found, otherwise it returns an empty Optional.
    Optional<UserEntity> findByEmail(String email);

    // This method is used to find a user by their username or email.
    // It returns an Optional that contains the UserEntity if found, otherwise it returns an empty Optional.
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    // This method is used to find a user by their ID.
    // It returns an Optional that contains the UserEntity if found, otherwise it returns an empty Optional.
    @SuppressWarnings("null")
    Optional<UserEntity> findById(Long id);
}
