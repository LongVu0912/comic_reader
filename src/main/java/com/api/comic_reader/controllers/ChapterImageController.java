package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // This method handles the POST request to insert images for a chapter. It accepts a list of images and a chapter
    // ID.
    @PostMapping("/insertChapterImages/{chapterId}")
    public ResponseEntity<ApiResponse> insertChapterImage(
            @PathVariable Long chapterId, @RequestParam("imageData") List<MultipartFile> imageDataList)
            throws AppException {
        // Loop through each image file in the list and add it to the system
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

    // This method handles the GET request to get an image by its ID. It returns the image as a byte array.
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws AppException {
        byte[] image = chapterImageService.getImageFromImageId(imageId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    // This method handles the DELETE request to delete an image by its ID.
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
