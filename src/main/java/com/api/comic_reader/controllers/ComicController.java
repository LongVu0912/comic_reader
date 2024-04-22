package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.requests.ComicRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.services.ComicService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/insertComic")
    public ResponseEntity<ApiResponse> insertComic(
            @RequestPart("name") String name,
            @RequestPart("author") String author,
            @RequestPart("description") String description,
            @RequestPart("imageData") MultipartFile imageData) throws Exception {

        ComicRequest newComic = new ComicRequest();
        newComic.setName(name);
        newComic.setAuthor(author);
        newComic.setDescription(description);
        newComic.setThumbnailImage(imageData);

        comicService.insertComic(newComic);

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Insert comic successfully")
                        .result(null)
                        .build());
    }

    @GetMapping("/thumbnail/{comicId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long comicId) throws Exception {
        byte[] thumbnail = comicService.getThumbnailImage(comicId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(thumbnail);
    }
}
