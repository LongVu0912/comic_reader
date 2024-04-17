package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.dtos.responses.ResponseObject;
import com.api.comic_reader.services.ComicUserService;
import com.api.comic_reader.dtos.requests.RegisterRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicController {

    @Autowired
    private final ComicUserService comicUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody RegisterRequest newComicUser) throws Exception {
        ComicUserEntity newComicUserResponse = null;
        try {
            newComicUserResponse = comicUserService.register(newComicUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject
                            .builder()
                            .message(e.getMessage())
                            .data("")
                            .build());
        }
        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Register successfully")
                        .data(newComicUserResponse)
                        .build());
    }
}
