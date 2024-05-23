package com.api.comic_reader.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.comic_reader.dtos.requests.LoginRequest;
import com.api.comic_reader.dtos.requests.OtpRequest;
import com.api.comic_reader.dtos.requests.RegisterRequest;
import com.api.comic_reader.dtos.requests.ResetPasswordRequest;
import com.api.comic_reader.dtos.requests.TokenRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.AuthResponse;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.services.AuthenticationService;
import com.api.comic_reader.services.ResetPasswordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest newUser) throws AppException {
        authenticationService.register(newUser);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Register successfully")
                        .result(null)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) throws AppException {
        AuthResponse loginResponse = authenticationService.login(loginRequest);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Login successfully")
                        .result(loginResponse)
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody TokenRequest logoutRequest) throws AppException {
        authenticationService.logout(logoutRequest.getToken());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Logout successfully")
                        .result(null)
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody TokenRequest refreshRequest) throws AppException {
        AuthResponse authenticationResponse = authenticationService.refreshToken(refreshRequest.getToken());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Refresh token successfully")
                        .result(authenticationResponse)
                        .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse> introspect(@RequestBody TokenRequest introspectRequest) throws AppException {
        IntrospectResponse introspectResponse = authenticationService.introspect(introspectRequest);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Introspect successfully")
                        .result(introspectResponse)
                        .build());
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest)
            throws AppException {

        resetPasswordService.resetPassword(resetPasswordRequest);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Otp has been sent to your email")
                        .result(null)
                        .build());
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody OtpRequest otpRequest) throws AppException {
        resetPasswordService.verifyOtp(otpRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("OTP verified successfully, new password has been sent to your email")
                        .result(null)
                        .build());
    }
}
