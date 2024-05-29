package com.api.comic_reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.comic_reader.entities.InvalidatedTokenEntity;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects.
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedTokenEntity, String> {
    // This interface extends JpaRepository which provides methods to perform CRUD operations on InvalidatedTokenEntity.
    // No additional methods are declared here, so all operations will use the standard methods provided by
    // JpaRepository.
}
