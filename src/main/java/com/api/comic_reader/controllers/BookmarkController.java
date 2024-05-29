package com.api.comic_reader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.BookmarkRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookmarkController {
    @Autowired
    private final BookmarkService bookmarkService;

    // This method handles the POST request to bookmark a comic.
    // If the comic is already bookmarked, it will be unbookmarked.
    @PostMapping("/bookmarkComic")
    public ResponseEntity<ApiResponse> bookmarkChapter(@RequestBody BookmarkRequest bookmarkRequest)
            throws AppException {
        boolean isBookmark = bookmarkService.bookmarkComic(bookmarkRequest.getComicId());

        String message = isBookmark ? "Bookmark chapter successfully" : "Unbookmark chapter successfully";

        return ResponseEntity.ok()
                .body(ApiResponse.builder().message(message).result(null).build());
    }

    // This method handles the GET request to get all the bookmarks of the current user.
    @GetMapping("/getMyBookmarks")
    public ResponseEntity<ApiResponse> getMyBookmarks() {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get my bookmarks successfully")
                        .result(bookmarkService.getMyBookmarks())
                        .build());
    }
}
