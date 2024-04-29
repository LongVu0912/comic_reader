package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.ChapterRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.services.ChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChapterController {

    @Autowired
    private final ChapterService chapterService;

    @PostMapping("/insertChapter")
    public ResponseEntity<ApiResponse> insertChapter(@RequestBody ChapterRequest newChapter) {
        chapterService.insertChapter(newChapter);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Insert chapter successfully")
                        .result(null)
                        .build());
    }

    @GetMapping("/getComicChapters/{id}")
    public ResponseEntity<ApiResponse> getComicChapters(@PathVariable Long id) {
        List<ChapterResponse> comics = chapterService.getComicChapters(id);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all comics successfully")
                        .result(comics)
                        .build());
    }

    @GetMapping("/getLastestChapter/{comicId}")
    public ResponseEntity<ApiResponse> getLastestChapter(@PathVariable Long comicId) {
        ChapterResponse chapters = chapterService.getLastestChapter(comicId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get lastest chapters successfully")
                        .result(chapters)
                        .build());
    }
}
