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

    public void insertChapterImage(ChapterImageRequest newChapterImage) throws Exception {
        ChapterEntity chapter = chapterRepository.findById(newChapterImage.getChapterId()).get();
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
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<String> getChapterImageUrls(Long chapterId) {
        ChapterEntity chapter = chapterRepository.findById(chapterId).get();
        List<ChapterImageEntity> chapterImages = chapterImageRepository.findByChapter(chapter);

        String baseUrl = EnvironmentVariable.baseUrl;

        List<String> imageUrls = chapterImages.stream().map(chapterImage -> baseUrl + "/api/comic/image/" + chapterImage.getId())
                .toList();
        return imageUrls;
    }

    public byte[] getImageFromImageId(Long imageId) {
        try {
            ChapterImageEntity chapterImage = chapterImageRepository.findById(imageId).get();
            return chapterImage.getImageData();
        } catch (Exception e) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }
}
