package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.requests.LoginRequest;
import com.api.comic_reader.dtos.requests.TokenRequest;
import com.api.comic_reader.dtos.requests.RefreshRequest;
import com.api.comic_reader.dtos.requests.RegisterRequest;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.dtos.responses.AuthResponse;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.AuthenticationService;
import com.api.comic_reader.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest newUser) throws AppException {
        UserEntity newUserResponse = userService.register(newUser);

        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Register successfully")
                        .result(newUserResponse)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) throws AppException {
        AuthResponse loginResponse = authenticationService.login(loginRequest);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Login successfully")
                .result(loginResponse)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody TokenRequest logoutRequest) throws AppException {
        authenticationService.logout(logoutRequest.getToken());

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Logout successfully")
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) throws AppException {
        AuthResponse authenticationResponse = authenticationService.refreshToken(refreshRequest.getToken());

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Refresh token successfully")
                .result(authenticationResponse)
                .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse> introspect(@RequestBody TokenRequest introspectRequest) throws AppException {
        IntrospectResponse introspectResponse = authenticationService.introspect(introspectRequest);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Introspect successfully")
                .result(introspectResponse)
                .build());
    }
}
