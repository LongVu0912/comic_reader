package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.ComicRatingsDTO;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.RatingEntity;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.repositories.RatingRepository;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.responses.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComicRatingController {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ComicUserRepository comicUserRepository;
    @Autowired
    private ComicRepository comicRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findComicRating(@PathVariable Long id) {
        Optional<RatingEntity> comicRating = ratingRepository.findById(id);

        if(!comicRating.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .message("Comic rating not found...")
                            .data("")
                            .build());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("ratedBy", comicRating.get().getComicUser().getFullName());
        responseData.put("comicName", comicRating.get().getComic().getName());
        responseData.put("score", comicRating.get().getScore().toString());

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Find comic rating successfully")
                        .data(comicRating)
                        .build());

    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertComicRating(@RequestBody ComicRatingsDTO comicRating) {

        Optional<ComicUserEntity> comicUser = comicUserRepository.findById(comicRating.getComicUserId());
        Optional<ComicEntity> comic = comicRepository.findById(comicRating.getComicId());

        if(!comicUser.isPresent() || !comic.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject
                            .builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message("Comic or user not found...")
                            .data("")
                            .build()
            );
        }

        RatingEntity result = ratingRepository.save(
                RatingEntity
                        .builder()
                        .comic(comic.get())
                        .comicUser(comicUser.get())
                        .score(comicRating.getScore())
                        .build()
        );

        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .status(HttpStatus.OK)
                        .message("Insert a new comic rating successfully")
                        .data(result)
                        .build()
        );
    }
}
