package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.ComicUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComicUserController {

    @Autowired
    private final ComicUserService comicUserService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() throws Exception {
        List<ComicUserEntity> comicUsers = comicUserService.getAllUsers();

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get all user successfully")
                        .result(comicUsers)
                        .build());
    }

    @GetMapping("/getUserInformationById/{id}")
    public ResponseEntity<ApiResponse> getUserInformationById(@PathVariable Long id) {
        ComicUserEntity userInformation = comicUserService.getUserInformationById(id);
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get user information successfully")
                        .result(userInformation)
                        .build());
    }
}
