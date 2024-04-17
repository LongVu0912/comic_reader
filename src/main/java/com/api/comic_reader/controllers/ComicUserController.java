package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.dtos.responses.ResponseObject;
import com.api.comic_reader.services.ComicUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComicUserController {

    @Autowired
    private final ComicUserService comicUserService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<ComicUserEntity> comicUsers = comicUserService.getAllUsers();

        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Get all user successfully")
                        .data(comicUsers)
                        .build());
    }

    @GetMapping("/getUserInformationById/{id}")
    public ResponseEntity<ResponseObject> getUserInformationById(@PathVariable Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        log.info("Authorities: {}", authentication.getAuthorities());

        ComicUserEntity userInformation = comicUserService.getUserInformationById(id);
        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Get user information successfully")
                        .data(userInformation)
                        .build());
    }
}
