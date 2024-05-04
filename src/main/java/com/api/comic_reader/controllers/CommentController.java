package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.CommentRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.CommentResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @PostMapping("/leaveComment")
    public ResponseEntity<ApiResponse> leaveComment(@RequestBody CommentRequest newComment) throws AppException {
        commentService.leaveComment(newComment);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Leave comment successfully")
                        .build());
    }

    @GetMapping("/getCommentsOfChapter/{chapterId}")
    public ResponseEntity<ApiResponse> getComments(@PathVariable Long chapterId) throws AppException {
        List<CommentResponse> comments = commentService.getComments(chapterId);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comments successfully")
                        .result(comments)
                        .build());
    }
}
