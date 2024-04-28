package com.api.comic_reader.services;

import com.api.comic_reader.config.EnvironmentVariable;
import com.api.comic_reader.dtos.requests.ChapterImageRequest;
import com.api.comic_reader.entities.ChapterEntity;
import com.api.comic_reader.entities.ChapterImageEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ChapterImageRepository;
import com.api.comic_reader.repositories.ChapterRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChapterImageService {
    @Autowired
    private ChapterImageRepository chapterImageRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    public void insertChapterImage(ChapterImageRequest newChapterImage) throws AppException {
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
    public List<String> getChapterImageUrls(Long chapterId) {
        Optional<ChapterEntity> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        ChapterEntity chapter = chapterOptional.get();
        List<ChapterImageEntity> chapterImages = chapterImageRepository.findByChapter(chapter);

        String baseUrl = EnvironmentVariable.baseUrl;

        return chapterImages.stream()
                .map(chapterImage -> baseUrl + "/api/comic/image/" + chapterImage.getId())
                .toList();
    }

    public byte[] getImageFromImageId(Long imageId) {
        Optional<ChapterImageEntity> chapterImageOptional = chapterImageRepository.findById(imageId);
        if (chapterImageOptional.isEmpty()) {
            throw new AppException(ErrorCode.CHAPTER_IMAGES_NOT_FOUND);
        }
        ChapterImageEntity chapterImage = chapterImageOptional.get();
        return chapterImage.getImageData();
    }
}
