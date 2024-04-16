package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.responses.LoginResponse;
import com.api.comic_reader.responses.ResponseObject;
import com.api.comic_reader.services.ComicUserService;
import com.api.comic_reader.dtos.LoginDTO;
import com.api.comic_reader.dtos.RegisterDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comic-user")
@RequiredArgsConstructor
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

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody RegisterDTO newComicUser) throws Exception {
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

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDTO loginDTO) throws Exception {
        LoginResponse loginResponse = null;
        try {
            loginResponse = comicUserService.login(loginDTO);
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

    // @GetMapping("/{id}")
    // public ResponseEntity<ResponseObject> findComicUserById(@PathVariable Long
    // id) {
    // Optional<ComicUserEntity> comicUserEntity = comicUserRepository.findById(id);

    // return comicUserEntity.isPresent() ?
    // ResponseEntity.ok().body(
    // ResponseObject.builder()
    // .status(HttpStatus.OK)
    // .message("Find comic user successfully")
    // .data(comicUserEntity)
    // .build()) :
    // ResponseEntity.status(HttpStatus.NOT_FOUND).body(
    // ResponseObject
    // .builder()
    // .status(HttpStatus.NOT_FOUND)
    // .message("Comic user not found...")
    // .data("")
    // .build());
    // }
}
