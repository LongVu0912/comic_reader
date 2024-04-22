package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.requests.IntrospectRequest;
import com.api.comic_reader.dtos.requests.LoginRequest;
import com.api.comic_reader.dtos.requests.LogoutRequest;
import com.api.comic_reader.dtos.requests.RefreshRequest;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.dtos.responses.AuthResponse;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.services.AuthenticationService;

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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        AuthResponse loginResponse = authenticationService.login(loginRequest);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Login successfully")
                .result(loginResponse)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody LogoutRequest logoutRequest) throws Exception {
        authenticationService.logout(logoutRequest.getToken());

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Logout successfully")
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) throws Exception {
        AuthResponse authenticationResponse = authenticationService.refreshToken(refreshRequest.getToken());

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Refresh token successfully")
                .result(authenticationResponse)
                .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws Exception {
        IntrospectResponse introspectResponse = authenticationService.introspect(introspectRequest);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Introspect successfully")
                .result(introspectResponse)
                .build());
    }
}
