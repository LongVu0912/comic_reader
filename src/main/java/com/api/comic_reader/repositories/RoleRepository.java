package com.api.comic_reader.repositories;

import com.api.comic_reader.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String roleName);
}
