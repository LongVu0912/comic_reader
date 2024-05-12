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

    @PostMapping("/bookmarkComic")
    public ResponseEntity<ApiResponse> bookmarkChapter(@RequestBody BookmarkRequest bookmarkRequest)
            throws AppException {
        boolean isBookmark = bookmarkService.bookmarkComic(bookmarkRequest.getComicId());

        String message = isBookmark ? "Bookmark chapter successfully" : "Unbookmark chapter successfully";

        return ResponseEntity.ok()
                .body(ApiResponse.builder().message(message).result(null).build());
    }

    @GetMapping("/getMyBookmarks")
    public ResponseEntity<ApiResponse> getMyBookmarks() {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get my bookmarks successfully")
                        .result(bookmarkService.getMyBookmarks())
                        .build());
    }
}
