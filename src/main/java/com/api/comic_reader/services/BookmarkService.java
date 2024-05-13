package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.comic_reader.config.EnvVariables;
import com.api.comic_reader.dtos.responses.BookmarkResponse;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.entities.BookmarkEntity;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.BookmarkRepository;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterService chapterService;

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public boolean bookmarkComic(Long comicId) throws AppException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // Check if user exists
        Optional<UserEntity> user = userRepository.findByUsername(name);

        if (!user.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        // Check if chapter exists
        Optional<ComicEntity> comic = comicRepository.findById(comicId);

        if (!comic.isPresent()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        // Check if user has bookmarked this chapter before
        if (!bookmarkRepository.existsByComicAndUser(comic.get(), user.get())) {
            BookmarkKey id = new BookmarkKey();
            id.setComicId(comic.get().getId());
            id.setUserId(user.get().getId());

            bookmarkRepository.save(BookmarkEntity.builder()
                    .id(id)
                    .comic(comic.get())
                    .user(user.get())
                    .build());
            return true;
        } else {
            bookmarkRepository.deleteByComicAndUser(comic.get(), user.get());
            return false;
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public List<BookmarkResponse> getMyBookmarks() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // Check if user exists
        Optional<UserEntity> user = userRepository.findByUsername(name);

        if (!user.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        List<BookmarkEntity> bookmarks = bookmarkRepository.findByUser(user.get());

        return bookmarks.stream()
                .map(bookmark -> {
                    String thumbnailUrl = EnvVariables.baseUrl + "/api/comic/thumbnail/"
                            + bookmark.getComic().getId();
                    ChapterResponse lastChapter =
                            chapterService.getLastChapter(bookmark.getComic().getId());

                    return BookmarkResponse.builder()
                            .comicId(bookmark.getComic().getId())
                            .name(bookmark.getComic().getName())
                            .thumbnailUrl(thumbnailUrl)
                            .lastChapter(lastChapter)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
