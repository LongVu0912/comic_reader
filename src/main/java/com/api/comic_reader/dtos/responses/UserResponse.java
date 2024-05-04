package com.api.comic_reader.dtos.responses;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String dateOfBirth;

    private boolean isMale;

    List<BookmarkResponse> bookmarks;
}
