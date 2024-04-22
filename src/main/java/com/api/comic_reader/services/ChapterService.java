package com.api.comic_reader.services;

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

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChapterService {
    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private ChapterRepository chapterRespository;

    public void insertChapter(ChapterRequest newChapter) {
        ComicEntity comic = comicRepository.findById(newChapter.getComicId()).get();
        try {
            ChapterEntity chapter = ChapterEntity.builder()
                    .title(newChapter.getTitle())
                    .chapterNumber(newChapter.getChapterNumber())
                    .comic(comic)
                    .dateCreated(DateUtil.getCurrentDate())
                    .build();
            chapterRespository.save(chapter);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<ChapterResponse> getComicChapters(Long id) throws AppException {
        ComicEntity comic = null;
        try {
            comic = comicRepository.findById(id).get();
        } catch (Exception e) {
            throw new AppException(ErrorCode.COMIC_CHAPTERS_NOT_FOUND);
        }

        List<ChapterEntity> chapters = chapterRespository.findByComic(comic);

        return chapters.stream().map(chapter -> ChapterResponse.builder()
                .id(chapter.getId())
                .title(chapter.getTitle())
                .chapterNumber(chapter.getChapterNumber())
                .build()).collect(Collectors.toList());
    }
}
