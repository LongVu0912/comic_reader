package com.api.comic_reader.entities.composite_keys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.*;
// @Embeddable: This annotation is used to specify a class whose instances are stored as an intrinsic part of an owning
// entity and share the identity of the entity.
// @Getter: This annotation is used to generate getters for all fields in the class.
// @Setter: This annotation is used to generate setters for all fields in the class.
// @NoArgsConstructor: This annotation generates a constructor with no parameters.
// @AllArgsConstructor: This annotation generates a constructor with one parameter for each field.
// @Builder: This annotation produces complex builder APIs.
// @EqualsAndHashCode: This annotation generates implementations for the equals(Object other) and hashCode() methods
// inherited by all objects, based on relevant fields.

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookmarkKey implements Serializable {

    private Long comicId;

    private Long userId;
}
