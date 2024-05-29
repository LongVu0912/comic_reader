package com.api.comic_reader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.RatingRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.RatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingController {
    @Autowired
    private final RatingService ratingService;

    // This method handles the POST request to rate a comic
    @PostMapping("/rateComic")
    public ResponseEntity<ApiResponse> rateComic(@RequestBody RatingRequest newRating) throws AppException {
        ratingService.rateComic(newRating);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Rate comic successfully")
                        .result(null)
                        .build());
    }

    // This method handles the GET request to fetch the average rating of a comic
    @GetMapping("/getComicAverageRating/{comicId}")
    public ResponseEntity<ApiResponse> getComicAverageRating(@PathVariable Long comicId) {
        Double averageRating = ratingService.getComicAverageRating(comicId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comic average rating successfully")
                        .result(averageRating)
                        .build());
    }
}
