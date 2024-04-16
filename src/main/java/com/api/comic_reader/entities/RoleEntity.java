package com.api.comic_reader.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Collection<ComicUserEntity> comicUsers;

}
