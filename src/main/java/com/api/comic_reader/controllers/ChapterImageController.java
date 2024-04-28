package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.requests.ChapterImageRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.ChapterImageService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/comic/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChapterImageController {

    @Autowired
    private final ChapterImageService chapterImageService;

    @PostMapping("/insertChapterImage")
    public ResponseEntity<ApiResponse> insertChapterImage(
            @RequestParam("chapterId") String chapterId,
            @RequestParam("imageData") List<MultipartFile> imageDataList) throws AppException {
        Long chapterIdLong = Long.parseLong(chapterId);

        // Lặp qua từng file ảnh trong danh sách và thực hiện thêm vào hệ thống
        for (MultipartFile imageData : imageDataList) {
            chapterImageService.insertChapterImage(ChapterImageRequest.builder()
                    .chapterId(chapterIdLong)
                    .imageData(imageData)
                    .build());
        }

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Insert chapter images successfully")
                        .result(null)
                        .build());
    }

    @GetMapping("/getChapterImageUrls/{chapterId}")
    public ResponseEntity<ApiResponse> getChapterImages(@PathVariable Long chapterId) {
        List<String> imageUrls = chapterImageService.getChapterImageUrls(chapterId);

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get all chapter images successfully")
                        .result(imageUrls)
                        .build());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws AppException {
        byte[] image = chapterImageService.getImageFromImageId(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
