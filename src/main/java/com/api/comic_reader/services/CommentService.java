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

import com.api.comic_reader.config.EnvVariables;
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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

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

    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public void leaveComment(CommentRequest newComment) throws AppException {
        if (newComment.getContent() == null || newComment.getContent().length() < EnvVariables.minCommentLength) {
            throw new AppException(ErrorCode.INVALID_COMMENT);
        }
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(newComment.getChapterId());
        if (!chapterOptional.isPresent()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Optional<UserEntity> userOptional = userRepository.findByUsername(name);

        if (!userOptional.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        commentRepository.save(CommentEntity.builder()
                .user(userOptional.get())
                .chapter(chapterOptional.get())
                .createdAt(DateUtil.getCurrentDate())
                .content(newComment.getContent())
                .build());
    }

    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public List<CommentResponse> getCommentsOfChapter(Long chapterId) throws AppException {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (!chapterOptional.isPresent()) {
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
