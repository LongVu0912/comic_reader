package com.api.comic_reader.controllers;


import com.api.comic_reader.repositories.ChapterRepository;
import com.api.comic_reader.responses.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChapterController {

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAllChapter() {
        return null;
    }
}
