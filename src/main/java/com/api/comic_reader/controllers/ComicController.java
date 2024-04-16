package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.responses.ResponseObject;
import com.api.comic_reader.services.ComicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comic")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComicController {
    @Autowired
    private ComicService comicService;

    @GetMapping("")
    public String getAllComics() {
        try {
        } catch (Exception e) {

        }
        return "Hello World";
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addComic(@RequestBody ComicEntity comic){

        try {
            // ComicEntity result = comicService.insertComic(comic);

            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Insert a new comic successfully")
                            .data("")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message(e.getMessage())
                            .data("")
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getComicById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Find comic successfully")
                        .data(comicService.findComicById(id))
                        .build()
        );
    }
}
