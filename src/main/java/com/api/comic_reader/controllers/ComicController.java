package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.services.ComicService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comic")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComicController {

    @Autowired
    private final ComicService comicService;

    @GetMapping("/getAllComics")
    public ResponseEntity<ApiResponse> getAllComics() {
        List<ComicResponse> comics = comicService.getAllComics();

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get all comics successfully")
                        .result(comics)
                        .build());
    }
}
