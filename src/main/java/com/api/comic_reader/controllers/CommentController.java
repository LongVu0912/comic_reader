package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.comic_reader.dtos.requests.CommentRequest;
import com.api.comic_reader.dtos.requests.DeleteCommentRequest;
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

    @GetMapping("/getAllComments")
    public ResponseEntity<ApiResponse> getAllComments() throws AppException {
        List<CommentResponse> comments = commentService.getAllComments();
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all comments successfully")
                        .result(comments)
                        .build());
    }

    @PostMapping("/leaveComment")
    public ResponseEntity<ApiResponse> leaveComment(@RequestBody CommentRequest newComment) throws AppException {
        commentService.leaveComment(newComment);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Leave comment successfully")
                        .build());
    }

    @GetMapping("/getCommentsOfChapter/{chapterId}")
    public ResponseEntity<ApiResponse> getCommentsOfChapter(@PathVariable Long chapterId) throws AppException {
        List<CommentResponse> comments = commentService.getCommentsOfChapter(chapterId);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comments successfully")
                        .result(comments)
                        .build());
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<ApiResponse> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest)
            throws AppException {
        commentService.deleteComment(deleteCommentRequest.getCommentId());
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Delete comment successfully")
                        .result(null)
                        .build());
    }
}
