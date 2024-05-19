package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.api.comic_reader.dtos.requests.ChapterImageRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.ChapterImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChapterImageController {

    @Autowired
    private final ChapterImageService chapterImageService;

    @PostMapping("/insertChapterImages/{chapterId}")
    public ResponseEntity<ApiResponse> insertChapterImage(
            @PathVariable Long chapterId, @RequestParam("imageData") List<MultipartFile> imageDataList)
            throws AppException {
        // Lặp qua từng file ảnh trong danh sách và thực hiện thêm vào hệ thống
        for (MultipartFile imageData : imageDataList) {
            chapterImageService.insertChapterImages(ChapterImageRequest.builder()
                    .chapterId(chapterId)
                    .imageData(imageData)
                    .build());
        }

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Insert chapter images successfully")
                        .result(null)
                        .build());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws AppException {
        byte[] image = chapterImageService.getImageFromImageId(imageId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @DeleteMapping("/deleteChapterImages/{imageId}")
    public ResponseEntity<ApiResponse> deleteChapterImage(@PathVariable Long imageId) throws AppException {
        chapterImageService.deleteChapterImages(imageId);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Delete chapter images successfully")
                        .result(null)
                        .build());
    }
}
