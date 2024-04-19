package com.api.comic_reader.services;

import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ChapterRepository;
import com.api.comic_reader.repositories.ComicRepository;

import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.ArrayList;
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

    public List<String> getChapterImages(Long id) {
        ChapterEntity chapter = chapterRespository.findById(id).get();
        Long comicId = chapter.getComic().getId();
        ComicEntity comic = comicRepository.findById(comicId).get();

        String imagePath = comic.getImagePath();
        Long chapterNumber = chapter.getChapterNumber();
        Long pageQuantity = chapter.getPageQuantity();

        String baseResource = "";

        List<String> imagesPath = new ArrayList<>();

        if (pageQuantity == null) {
            throw new AppException(ErrorCode.CHAPTER_IMAGES_NOT_FOUND);
        }

        for (int i = 1; i <= pageQuantity; i++) {
            String path = baseResource + imagePath + "/" + "chapter" + chapterNumber + "/" + i + ".jpg";
            imagesPath.add(path);
        }

        return imagesPath;
    }
}
