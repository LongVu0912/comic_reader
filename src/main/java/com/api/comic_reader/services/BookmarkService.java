package com.api.comic_reader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import com.api.comic_reader.repositories.BookmarkRepository;
import com.api.comic_reader.repositories.ChapterRepository;
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
    private ChapterRepository chapterRepository;

    // @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    // public void bookmarkChapter(Long chapterId) throws AppException {
    //     var context = SecurityContextHolder.getContext();
    //     String name = context.getAuthentication().getName();

    //     // Check if user exists
    //     Optional<UserEntity> user = userRepository.findByUsername(name);

    //     if (!user.isPresent()) {
    //         throw new AppException(ErrorCode.USER_NOT_FOUND);
    //     }

    //     // Check if chapter exists
    //     Optional<ChapterEntity> chapter = chapterRepository.findById(chapterId);

    //     if (!chapter.isPresent()) {
    //         throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
    //     }

    //     // Check if user has bookmarked this chapter before
    //     if (!bookmarkRepository.existsByChapterAndUser(chapter.get(), user.get())) {
    //         BookmarkKey id = new BookmarkKey();
    //         id.setChapterId(chapter.get().getId());
    //         id.setUserId(user.get().getId());

    //         bookmarkRepository.save(BookmarkEntity.builder()
    //                 .id(id)
    //                 .chapter(chapter.get())
    //                 .user(user.get())
    //                 .build());
    //     } else {
    //         throw new AppException(ErrorCode.BOOKMARK_EXISTS);
    //     }
    // }

    // @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    // public List<BookmarkResponse> getMyBookmarks() {
    //     var context = SecurityContextHolder.getContext();
    //     String name = context.getAuthentication().getName();

    //     // Check if user exists
    //     Optional<UserEntity> user = userRepository.findByUsername(name);

    //     if (!user.isPresent()) {
    //         throw new AppException(ErrorCode.USER_NOT_FOUND);
    //     }

    //     List<BookmarkEntity> bookmarks = bookmarkRepository.findByUser(user.get());

    //     return bookmarks.stream()
    //             .map(bookmark -> BookmarkResponse.builder()
    //                     .chapterId(bookmark.getChapter().getId())
    //                     .chapterTitle(bookmark.getChapter().getTitle())
    //                     .chapterNumber(bookmark.getChapter().getChapterNumber())
    //                     .build())
    //             .toList();
    // }
}
