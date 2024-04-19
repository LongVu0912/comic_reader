package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.UserService;
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
    private final UserService comicUserService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest newComicUser) throws Exception {
        UserEntity newComicUserResponse = comicUserService.register(newComicUser);

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Register successfully")
                        .result(newComicUserResponse)
                        .build());
    }
}
