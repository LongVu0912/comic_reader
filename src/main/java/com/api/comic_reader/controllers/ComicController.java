package com.api.comic_reader.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.api.comic_reader.dtos.requests.ComicRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicInformationResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.ComicService;

import lombok.RequiredArgsConstructor;

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

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all comics successfully")
                        .result(comics)
                        .build());
    }

    @GetMapping("/getFinishedComics")
    public ResponseEntity<ApiResponse> getFinishedComics() {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicResponse> finishedComics =
                comics.stream().filter(ComicResponse::isFinished).collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get finished comics successfully")
                        .result(finishedComics)
                        .build());
    }

    @GetMapping("/getNotFinishedComics")
    public ResponseEntity<ApiResponse> getNotFinishedComics() {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicResponse> notFinishedComics =
                comics.stream().filter(comic -> !comic.isFinished()).collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get not finished comics successfully")
                        .result(notFinishedComics)
                        .build());
    }

    @GetMapping("/get6LastComics")
    public ResponseEntity<ApiResponse> get6LastComics() {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicResponse> list6LastComics = comics.stream()
                .filter(comic -> comic.getLastChapter() != null)
                .sorted(Comparator.comparing(
                        comic -> comic.getLastChapter().getCreatedAt(),
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(6)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get 6 last comics successfully")
                        .result(list6LastComics)
                        .build());
    }

    @GetMapping("/get3MostViewComics")
    public ResponseEntity<ApiResponse> get3MostViewComics() {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicResponse> list3MostViewComics = comics.stream()
                .filter(comic -> comic.getLastChapter() != null)
                .sorted(Comparator.comparing(ComicResponse::getView, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(3)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get 3 most view comics successfully")
                        .result(list3MostViewComics)
                        .build());
    }

    @PostMapping("/insertComic")
    public ResponseEntity<ApiResponse> insertComic(
            @RequestPart("name") String name,
            @RequestPart("author") String author,
            @RequestPart("description") String description,
            @RequestPart("imageData") MultipartFile imageData)
            throws AppException {

        ComicRequest newComic = new ComicRequest();
        newComic.setName(name);
        newComic.setAuthor(author);
        newComic.setDescription(description);
        newComic.setThumbnailImage(imageData);

        comicService.insertComic(newComic);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Insert comic successfully")
                        .result(null)
                        .build());
    }

    @GetMapping("/thumbnail/{comicId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long comicId) throws AppException {
        byte[] thumbnail = comicService.getThumbnailImage(comicId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(thumbnail);
    }

    @GetMapping("/searchComics/{keyword}")
    public ResponseEntity<ApiResponse> searchComics(@PathVariable String keyword) {
        List<ComicResponse> comics = comicService.searchComics(keyword);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Search comics successfully")
                        .result(comics)
                        .build());
    }

    @GetMapping("/getComicInformation/{comicId}")
    public ResponseEntity<ApiResponse> getComicInformation(@PathVariable Long comicId) {
        ComicInformationResponse comic = comicService.getComicInformation(comicId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comic information successfully")
                        .result(comic)
                        .build());
    }
}
