package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.GenresRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.dtos.responses.GenreResponse;
import com.api.comic_reader.services.GenreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GenreController {
    @Autowired
    private final GenreService genreService;

    @GetMapping("/getAllGenres")
    public ResponseEntity<ApiResponse> getAllGenres() {
        List<GenreResponse> genres = genreService.getAllGenres();
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all genres successfully")
                        .result(genres)
                        .build());
    }

    @GetMapping("/getComicsByGenre/{genreId}")
    public ResponseEntity<ApiResponse> getComicsByGenre(@PathVariable Long genreId) {
        List<ComicResponse> comics = genreService.getComicsByGenre(genreId);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comics by genre successfully")
                        .result(comics)
                        .build());
    }

    @GetMapping("/getComicsByGenres")
    public ResponseEntity<ApiResponse> getComicsByGenres(@RequestBody GenresRequest genresRequest) {
        List<ComicResponse> comics = genreService.getComicsByGenres(genresRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comics by genres successfully")
                        .result(comics)
                        .build());
    }
}
