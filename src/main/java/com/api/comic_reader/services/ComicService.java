package com.api.comic_reader.services;

import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.repositories.ComicRepository;

import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    public List<ComicResponse> getAllComics() {
        List<ComicEntity> comics = comicRepository.findAll();

        List<ComicResponse> comicsRespone = comics.stream().map(comic -> {
            return ComicResponse.builder()
                    .id(comic.getId())
                    .name(comic.getName())
                    .author(comic.getAuthor())
                    .description(comic.getDescription())
                    .thumbnailPath(comic.getImagePath() + "/thumb.jpg")
                    .view(comic.getView())
                    .isDeleted(comic.getIsDeleted())
                    .isFinished(comic.getIsFinished())
                    .build();
        }).collect(Collectors.toList());

        return comicsRespone;
    }
}
