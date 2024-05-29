package com.api.comic_reader.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.ChapterRequest;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ChapterRepository;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    // This method allows an admin to insert a new chapter into a comic.
    // It checks if the comic exists and is not finished.
    // It saves the new chapter to the database.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void insertChapter(ChapterRequest newChapter) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(newChapter.getComicId());
        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }
        if (comicOptional.get().getIsFinished()) {
            throw new AppException(ErrorCode.COMIC_ALREADY_FINISHED);
        }
        ComicEntity comic = comicOptional.get();
        try {
            ChapterEntity chapter = ChapterEntity.builder()
                    .title(newChapter.getTitle())
                    .chapterNumber(newChapter.getChapterNumber())
                    .comic(comic)
                    .createdAt(DateUtil.getCurrentDate())
                    .build();
            chapterRepository.save(chapter);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    // This method returns all chapters of a comic with the given ID.
    // It checks if the comic exists and is not deleted.
    // It maps each chapter to a ChapterResponse object.
    public List<ChapterResponse> getComicChapters(Long comicId) throws AppException {
        // Find comic by id
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        // Get all chapters of comic
        List<ChapterEntity> chapters = comic.getChapters();

        // If comic has no chapter
        if (chapters.isEmpty()) {
            return Collections.emptyList();
        }

        // Convert chapter entity to chapter response
        return chapters.stream()
                .map(chapter -> ChapterResponse.builder()
                        .id(chapter.getId())
                        .title(chapter.getTitle())
                        .chapterNumber(chapter.getChapterNumber())
                        .createdAt(DateUtil.convertDateToString(chapter.getCreatedAt()))
                        .imageUrls(null)
                        .build())
                .collect(Collectors.toList());
    }

    // This method returns the last chapter of a comic with the given ID.
    // It checks if the comic exists and is not deleted.
    // It maps the chapter to a ChapterResponse object.
    public ChapterResponse getLastChapter(Long comicId) {
        // Find comic by id
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        // Get last chapter of comic
        Optional<ChapterEntity> chapterEntity = chapterRepository.findTopByComicOrderByCreatedAtDesc(comic);
        ChapterEntity lastChapter = chapterEntity.orElse(null);
        if (lastChapter == null) {
            return null;
        }

        // Convert chapter entity to chapter response
        return ChapterResponse.builder()
                .id(lastChapter.getId())
                .title(lastChapter.getTitle())
                .chapterNumber(lastChapter.getChapterNumber())
                .createdAt(DateUtil.convertDateToString(lastChapter.getCreatedAt()))
                .build();
    }

    // This method allows an admin to delete a chapter with the given ID.
    // It checks if the chapter exists and if the comic is not finished.
    // It deletes the chapter from the database.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteChapter(Long chapterId) {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        if (chapterOptional.get().getComic().getIsFinished()) {
            throw new AppException(ErrorCode.COMIC_ALREADY_FINISHED);
        }
        ChapterEntity chapter = chapterOptional.get();
        chapterRepository.delete(chapter);
    }

    // This method allows an admin to edit a chapter with the given ID.
    // It checks if the chapter exists.
    // It updates the chapter in the database.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void editChapter(Long chapterId, ChapterRequest editChapter) {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        ChapterEntity chapter = chapterOptional.get();
        chapter.setTitle(editChapter.getTitle());
        chapter.setChapterNumber(editChapter.getChapterNumber());
        chapterRepository.save(chapter);
    }
}
