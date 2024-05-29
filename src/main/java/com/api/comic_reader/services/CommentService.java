package com.api.comic_reader.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.CommentRequest;
import com.api.comic_reader.dtos.responses.CommentResponse;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.CommentEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ChapterRepository;
import com.api.comic_reader.repositories.CommentRepository;
import com.api.comic_reader.repositories.UserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity()
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

    // This method returns all comments in the database.
    // It requires the user to have ADMIN authority.
    // It throws an exception if no comments are found.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<CommentResponse> getAllComments() throws AppException {
        List<CommentEntity> comments = commentRepository.findAll();

        if (comments.isEmpty()) {
            throw new AppException(ErrorCode.NO_COMMENT);
        }

        List<CommentResponse> commentsResponse = comments.stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .chapterId(comment.getChapter().getId())
                        .userId(comment.getUser().getId())
                        .fullName(comment.getUser().getFullName())
                        .content(comment.getContent())
                        .createdAt(DateUtil.convertDateToString(comment.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());

        Collections.reverse(commentsResponse);

        return commentsResponse;
    }

    // This method deletes a comment with the given ID.
    // It requires the user to have ADMIN authority.
    // It throws an exception if the comment is not found.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteComment(Long commentId) throws AppException {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.delete(commentOptional.get());
    }

    // This method allows a user to leave a comment on a chapter.
    // It requires the user to have either USER or ADMIN authority.
    // It throws an exception if the comment content is less than 8 characters, if the chapter is not found, or if the
    // user is not found.
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public void leaveComment(CommentRequest newComment) throws AppException {
        if (newComment.getContent().length() < 8L) {
            throw new AppException(ErrorCode.INVALID_COMMENT);
        }
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(newComment.getChapterId());
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Optional<UserEntity> userOptional = userRepository.findByUsername(name);

        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        commentRepository.save(CommentEntity.builder()
                .user(userOptional.get())
                .chapter(chapterOptional.get())
                .createdAt(DateUtil.getCurrentDate())
                .content(newComment.getContent())
                .build());
    }

    // This method returns all comments of a chapter with the given ID.
    // It requires the user to have either USER or ADMIN authority.
    // It throws an exception if the chapter is not found or if no comments are found.
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public List<CommentResponse> getCommentsOfChapter(Long chapterId) throws AppException {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        List<CommentEntity> comments = commentRepository.findByChapter(chapterOptional.get());

        if (comments.isEmpty()) {
            throw new AppException(ErrorCode.NO_COMMENT);
        }

        List<CommentResponse> commentsResponse = comments.stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .chapterId(comment.getChapter().getId())
                        .userId(comment.getUser().getId())
                        .fullName(comment.getUser().getFullName())
                        .content(comment.getContent())
                        .createdAt(DateUtil.convertDateToString(comment.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());

        Collections.reverse(commentsResponse);

        return commentsResponse;
    }
}
