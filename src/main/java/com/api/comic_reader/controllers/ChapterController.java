package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.ChapterRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.services.ChapterImageService;
import com.api.comic_reader.services.ChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChapterController {

    @Autowired
    private final ChapterService chapterService;

    @Autowired
    private final ChapterImageService chapterImageService;

    // This method handles the POST request to insert a new chapter.
    @PostMapping("/insertChapter")
    public ResponseEntity<ApiResponse> insertChapter(@RequestBody ChapterRequest newChapter) {
        chapterService.insertChapter(newChapter);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Insert chapter successfully")
                        .result(null)
                        .build());
    }

    // This method handles the GET request to get all images of a chapter.
    @GetMapping("/getChapter/{chapterId}")
    public ResponseEntity<ApiResponse> getChapterImages(@PathVariable Long chapterId) {
        ChapterResponse chapter = chapterImageService.getChapterImageUrls(chapterId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all chapter images successfully")
                        .result(chapter)
                        .build());
    }

    // This method handles the GET request to get all chapters of a comic.
    @GetMapping("/getComicChapters/{id}")
    public ResponseEntity<ApiResponse> getComicChapters(@PathVariable Long id) {
        List<ChapterResponse> chapters = chapterService.getComicChapters(id);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all chapters of comic successfully")
                        .result(chapters)
                        .build());
    }

    // This method handles the GET request to get the last chapter of a comic.
    @GetMapping("/getLastChapter/{comicId}")
    public ResponseEntity<ApiResponse> getLastChapter(@PathVariable Long comicId) {
        ChapterResponse chapters = chapterService.getLastChapter(comicId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get last chapters successfully")
                        .result(chapters)
                        .build());
    }

    // This method handles the DELETE request to delete a chapter.
    @DeleteMapping("/deleteChapter/{chapterId}")
    public ResponseEntity<ApiResponse> deleteChapter(@PathVariable Long chapterId) {
        chapterService.deleteChapter(chapterId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Delete chapter successfully")
                        .result(null)
                        .build());
    }

    // This method handles the PUT request to edit a chapter.
    @PutMapping("/editChapter/{chapterId}")
    public ResponseEntity<ApiResponse> editChapter(
            @PathVariable Long chapterId, @RequestBody ChapterRequest editChapterRequest) {
        chapterService.editChapter(chapterId, editChapterRequest);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Edit chapter successfully")
                        .result(null)
                        .build());
    }
}
