package com.api.comic_reader.entities.composite_keys;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookmarkKey implements Serializable {

    private Long chapterId;

    private Long comicUserId;

}
