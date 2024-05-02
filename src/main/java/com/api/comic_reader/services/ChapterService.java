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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChapterService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterRepository chapterRespository;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void insertChapter(ChapterRequest newChapter) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(newChapter.getComicId());
        if (comicOptional.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }
        ComicEntity comic = comicOptional.get();
        try {
            ChapterEntity chapter = ChapterEntity.builder()
                    .title(newChapter.getTitle())
                    .chapterNumber(newChapter.getChapterNumber())
                    .comic(comic)
                    .createdAt(DateUtil.getCurrentDate())
                    .build();
            chapterRespository.save(chapter);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<ChapterResponse> getComicChapters(Long comicId) throws AppException {
        // Find comic by id
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty()) {
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
                        .build())
                .collect(Collectors.toList());
    }

    public ChapterResponse getLastestChapter(Long comicId) {
        // Find comic by id
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        // Get lastest chapter of comic
        Optional<ChapterEntity> chapterEntity = chapterRespository.findTopByComicOrderByCreatedAtDesc(comic);
        ChapterEntity lastestChapter = chapterEntity.orElse(null);
        if (lastestChapter == null) {
            return null;
        }

        // Convert chapter entity to chapter response
        return ChapterResponse.builder()
                .id(lastestChapter.getId())
                .title(lastestChapter.getTitle())
                .chapterNumber(lastestChapter.getChapterNumber())
                .createdAt(DateUtil.convertDateToString(lastestChapter.getCreatedAt()))
                .build();
    }
}
