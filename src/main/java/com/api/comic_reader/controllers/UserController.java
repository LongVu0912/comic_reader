package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.dtos.requests.ChangeInformationRequest;
import com.api.comic_reader.dtos.requests.ChangePasswordRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() throws AppException {
        List<UserEntity> users = userService.getAllUsers();

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get all user successfully")
                        .result(users)
                        .build());
    }

    @GetMapping("/getUserInformationById/{id}")
    public ResponseEntity<ApiResponse> getUserInformationById(@PathVariable Long id) {
        UserEntity userInformation = userService.getUserInformationById(id);
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get user information successfully")
                        .result(userInformation)
                        .build());
    }

    @GetMapping("/getMyInformation")
    public ResponseEntity<ApiResponse> getMyInformation() {
        UserEntity userInformation = userService.getMyInformation();
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get user information successfully")
                        .result(userInformation)
                        .build());
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Change password successfully")
                        .result(null)
                        .build());
    }

    @PostMapping("/changeInformation")
    public ResponseEntity<ApiResponse> changeInformation(
            @RequestBody ChangeInformationRequest changeInformationRequest) {
        userService.changeInformation(changeInformationRequest);
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Change user information successfully")
                        .result(null)
                        .build());
    }
}
