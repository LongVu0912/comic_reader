package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private final UserService comicUserService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() throws Exception {
        List<UserEntity> comicUsers = comicUserService.getAllUsers();

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get all user successfully")
                        .result(comicUsers)
                        .build());
    }

    @GetMapping("/getUserInformationById/{id}")
    public ResponseEntity<ApiResponse> getUserInformationById(@PathVariable Long id) {
        UserEntity userInformation = comicUserService.getUserInformationById(id);
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get user information successfully")
                        .result(userInformation)
                        .build());
    }
}
