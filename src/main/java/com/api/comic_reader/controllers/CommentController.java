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

    // This method handles the GET request to get all comments.
    @GetMapping("/getAllComments")
    public ResponseEntity<ApiResponse> getAllComments() throws AppException {
        List<CommentResponse> comments = commentService.getAllComments();
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all comments successfully")
                        .result(comments)
                        .build());
    }

    // This method handles the POST request to leave a comment.
    @PostMapping("/leaveComment")
    public ResponseEntity<ApiResponse> leaveComment(@RequestBody CommentRequest newComment) throws AppException {
        commentService.leaveComment(newComment);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Leave comment successfully")
                        .build());
    }

    // This method handles the GET request to get all comments of a specific chapter.
    @GetMapping("/getCommentsOfChapter/{chapterId}")
    public ResponseEntity<ApiResponse> getCommentsOfChapter(@PathVariable Long chapterId) throws AppException {
        List<CommentResponse> comments = commentService.getCommentsOfChapter(chapterId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comments successfully")
                        .result(comments)
                        .build());
    }

    // This method handles the DELETE request to delete a comment.
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) throws AppException {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Delete comment successfully")
                        .result(null)
                        .build());
    }
}
