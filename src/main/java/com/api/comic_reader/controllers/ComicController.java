package com.api.comic_reader.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // This method handles the GET request to get all comics.
    @GetMapping("/getAllComics")
    public ResponseEntity<ApiResponse> getAllComics() {
        // Fetch all comics using the comic service
        List<ComicResponse> comics = comicService.getAllComics();

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get all comics successfully")
                        .result(comics)
                        .build());
    }

    // This method handles the GET request to get all finished comics.
    @GetMapping("/getFinishedComics")
    public ResponseEntity<ApiResponse> getFinishedComics() {
        // Fetch all comics using the comic service
        List<ComicResponse> comics = comicService.getAllComics();
        // Filter the comics to only include finished ones
        List<ComicResponse> finishedComics =
                comics.stream().filter(ComicResponse::isFinished).collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get finished comics successfully")
                        .result(finishedComics)
                        .build());
    }

    // This method handles the GET request to get all not finished comics.
    @GetMapping("/getNotFinishedComics")
    public ResponseEntity<ApiResponse> getNotFinishedComics() {
        // Fetch all comics using the comic service
        List<ComicResponse> comics = comicService.getAllComics();
        // Filter the comics to only include not finished ones
        List<ComicResponse> notFinishedComics =
                comics.stream().filter(comic -> !comic.isFinished()).collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get not finished comics successfully")
                        .result(notFinishedComics)
                        .build());
    }

    // This method handles the GET request to get the 6 last comics.
    @GetMapping("/get6LastComics")
    public ResponseEntity<ApiResponse> get6LastComics() {
        // Fetch all comics using the comic service
        List<ComicResponse> comics = comicService.getAllComics();
        // Filter and sort the comics to get the 6 last ones
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

    // This method handles the GET request to get the 3 most viewed comics.
    @GetMapping("/get3MostViewComics")
    public ResponseEntity<ApiResponse> get3MostViewComics() {
        // Fetch all comics using the comic service
        List<ComicResponse> comics = comicService.getAllComics();
        // Filter and sort the comics to get the 3 most viewed ones
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

    // This method handles the POST request to insert a new comic.
    @PostMapping("/insertComic")
    public ResponseEntity<ApiResponse> insertComic(
            @RequestPart("name") String name,
            @RequestPart("author") String author,
            @RequestPart("description") String description,
            @RequestPart("imageData") MultipartFile imageData)
            throws AppException {
        // Create a new comic request with the provided details
        ComicRequest newComic = new ComicRequest();
        newComic.setName(name);
        newComic.setAuthor(author);
        newComic.setDescription(description);
        newComic.setThumbnailImage(imageData);
        // Use the comic service to insert the new comic
        comicService.insertComic(newComic);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Insert comic successfully")
                        .result(null)
                        .build());
    }

    // This method handles the GET request to get the thumbnail image of a comic.
    @GetMapping("/thumbnail/{comicId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long comicId) throws AppException {
        // Fetch the thumbnail image of the comic using the comic service
        byte[] thumbnail = comicService.getThumbnailImage(comicId);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(thumbnail);
    }

    // This method handles the GET request to search comics by a keyword.
    @GetMapping("/searchComics/{keyword}")
    public ResponseEntity<ApiResponse> searchComics(@PathVariable String keyword) {
        // Search for comics using the keyword and the comic service
        List<ComicResponse> comics = comicService.searchComics(keyword);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Search comics successfully")
                        .result(comics)
                        .build());
    }

    // This method handles the GET request to get the information of a comic.
    @GetMapping("/getComicInformation/{comicId}")
    public ResponseEntity<ApiResponse> getComicInformation(@PathVariable Long comicId) {
        // Fetch the information of the comic using the comic service
        ComicInformationResponse comic = comicService.getComicInformation(comicId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Get comic information successfully")
                        .result(comic)
                        .build());
    }

    // This method handles the PUT request to edit a comic.
    @PutMapping("/editComic/{comicId}")
    public ResponseEntity<ApiResponse> editComic(
            @PathVariable Long comicId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "imageData", required = false) MultipartFile imageData)
            throws AppException {
        // Create a new comic request with the provided details
        ComicRequest editComicRequest = new ComicRequest();
        editComicRequest.setName(name);
        editComicRequest.setAuthor(author);
        editComicRequest.setDescription(description);
        editComicRequest.setThumbnailImage(imageData);
        // Use the comic service to edit the comic
        comicService.editComic(comicId, editComicRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Edit comic successfully")
                        .result(null)
                        .build());
    }

    // This method handles the DELETE request to delete a comic.
    @DeleteMapping("/deleteComic/{comicId}")
    public ResponseEntity<ApiResponse> deleteComic(@PathVariable Long comicId) throws AppException {
        comicService.deleteComic(comicId);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Delete comic successfully")
                        .result(null)
                        .build());
    }

    // This method handles the PUT request to set a comic as finished or not finished.
    @PutMapping("/setIsFinished/{comicId}")
    public ResponseEntity<ApiResponse> setIsFinished(@PathVariable Long comicId) throws AppException {
        // Use the comic service to set the comic as finished or not finished
        Boolean isFinished = comicService.setIsFinished(comicId);
        // Return a success message based on the result
        if (!isFinished) {
            return ResponseEntity.ok()
                    .body(ApiResponse.builder()
                            .message("Set comic to unfinished successfully")
                            .result(null)
                            .build());
        }
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Set comic to finished successfully")
                        .result(null)
                        .build());
    }
}
