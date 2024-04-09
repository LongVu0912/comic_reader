package com.api.comic_reader.controllers;

import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.responses.ResponseObject;
import com.api.comic_reader.services.ComicUser.ComicUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
        List<ComicUserEntity> comicUserEntities = comicUserService.getAllUsers();

        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .status(HttpStatus.OK)
                        .message("Get all user successfully")                       
                        .data(comicUserEntities)
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody ComicUserEntity comicUser){
        comicUserService.register(comicUser);

        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .status(HttpStatus.OK)
                        .message("Insert a new user successfully")
                        .build()
        );
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<ResponseObject> findComicUserById(@PathVariable Long id) {
    //     Optional<ComicUserEntity> comicUserEntity = comicUserRepository.findById(id);

    //     return comicUserEntity.isPresent() ?
    //             ResponseEntity.ok().body(
    //                     ResponseObject.builder()
    //                             .status(HttpStatus.OK)
    //                             .message("Find comic user successfully")
    //                             .data(comicUserEntity)
    //                             .build()) :
    //             ResponseEntity.status(HttpStatus.NOT_FOUND).body(
    //                     ResponseObject
    //                             .builder()
    //                             .status(HttpStatus.NOT_FOUND)
    //                             .message("Comic user not found...")
    //                             .data("")
    //                             .build());
    // }
}
