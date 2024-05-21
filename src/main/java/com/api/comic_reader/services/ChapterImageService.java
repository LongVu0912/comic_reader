package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.comic_reader.dtos.requests.ChapterImageRequest;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ChapterImageEntity;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ChapterImageRepository;
import com.api.comic_reader.repositories.ChapterRepository;
import com.api.comic_reader.repositories.ComicRepository;

import lombok.RequiredArgsConstructor;

@Service
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ChapterImageService {
    @Autowired
    private ChapterImageRepository chapterImageRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Value("${app.base-url}")
    private String BASE_URL;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void insertChapterImages(ChapterImageRequest newChapterImage) throws AppException {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(newChapterImage.getChapterId());
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        ChapterEntity chapter = chapterOptional.get();
        try {
            // Find the highest image order for this chapter
            Long maxOrder = chapterImageRepository.findMaxImageOrder(newChapterImage.getChapterId());

            // If there is no image in this chapter, set the order to 1
            Long imageOrder = (maxOrder == null) ? 1 : maxOrder + 1;

            ChapterImageEntity chapterImage = ChapterImageEntity.builder()
                    .imageData(newChapterImage.getImageData().getBytes())
                    .chapter(chapter)
                    .imageOrder(imageOrder)
                    .build();
            chapterImageRepository.save(chapterImage);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Transactional
    public ChapterResponse getChapterImageUrls(Long chapterId) {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        ChapterEntity chapter = chapterOptional.get();

        // Increase the view count of the comic
        ComicEntity comic = chapter.getComic();
        comicRepository.increaseView(comic);

        List<ChapterImageEntity> chapterImages = chapterImageRepository.findByChapter(chapter);

        List<String> imageUrls = chapterImages.stream()
                .map(chapterImage -> BASE_URL + "/api/image/" + chapterImage.getId())
                .toList();

        return ChapterResponse.builder()
                .id(chapterId)
                .title(chapter.getTitle())
                .chapterNumber(chapter.getChapterNumber())
                .createdAt(chapter.getCreatedAt().toString())
                .imageUrls(imageUrls)
                .build();
    }

    public byte[] getImageFromImageId(Long imageId) {
        Optional<ChapterImageEntity> chapterImageOptional = chapterImageRepository.findById(imageId);
        if (chapterImageOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_IMAGES_NOT_FOUND);
        }
        ChapterImageEntity chapterImage = chapterImageOptional.get();
        return chapterImage.getImageData();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteChapterImages(Long imageId) {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(imageId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        List<ChapterImageEntity> chapterImages = chapterImageRepository.findByChapter(chapterOptional.get());

        for (ChapterImageEntity chapterImage : chapterImages) {
            chapterImageRepository.delete(chapterImage);
        }
    }
}
