package com.api.comic_reader.controllers;

import com.api.comic_reader.dtos.requests.IntrospectRequest;
import com.api.comic_reader.dtos.requests.LoginRequest;
import com.api.comic_reader.dtos.requests.LogoutRequest;
import com.api.comic_reader.dtos.requests.RefreshRequest;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.dtos.responses.LoginResponse;
import com.api.comic_reader.dtos.responses.ResponseObject;
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
    public ResponseEntity<ResponseObject> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = null;
        try {
            loginResponse = authenticationService.login(loginRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject
                            .builder()
                            .message(e.getMessage())
                            .data("")
                            .build());
        }

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Login successfully")
                .data(loginResponse)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(@RequestBody LogoutRequest logoutRequest) throws Exception {
        authenticationService.logout(logoutRequest.getToken());

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Logout successfully")
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseObject> refreshToken(@RequestBody RefreshRequest refreshRequest) throws Exception {
        LoginResponse authenticationResponse = null;

        try {
            authenticationResponse = authenticationService.refreshToken(refreshRequest.getToken());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject
                            .builder()
                            .message("Can not refresh token, please try again!")
                            .data("")
                            .build());
        }

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Refresh token successfully")
                .data(authenticationResponse)
                .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ResponseObject> introspect(@RequestBody IntrospectRequest introspectRequest) {
        IntrospectResponse introspectResponse = null;
        try {
            introspectResponse = authenticationService.introspect(introspectRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject
                            .builder()
                            .message(e.getMessage())
                            .data("")
                            .build());
        }

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Introspect successfully")
                .data(introspectResponse)
                .build());
    }
}
