package com.api.comic_reader.services;

import com.api.comic_reader.config.EnvironmentVariable;
import com.api.comic_reader.dtos.requests.ComicRequest;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;

import org.springframework.util.StringUtils;
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
            String thumbnailUrl = EnvironmentVariable.baseUrl + "/api/comic/thumbnail/" + comic.getId();

            return ComicResponse.builder()
                    .id(comic.getId())
                    .name(comic.getName())
                    .author(comic.getAuthor())
                    .description(comic.getDescription())
                    .thumbnailUrl(thumbnailUrl)
                    .view(comic.getView())
                    .isDeleted(comic.getIsDeleted())
                    .isFinished(comic.getIsFinished())
                    .build();
        }).collect(Collectors.toList());

        return comicsRespone;
    }

    public ComicEntity insertComic(ComicRequest newComic) throws AppException {      
        try {
            @SuppressWarnings("null")
            String fileName = StringUtils.cleanPath(newComic.getThumbnailImage().getOriginalFilename());
            if (fileName.contains("..")) {
                throw new AppException(ErrorCode.THUMBNAIL_INVALID);
            }

            ComicEntity comic = ComicEntity.builder()
                    .name(newComic.getName())
                    .author(newComic.getAuthor())
                    .view(0L)
                    .description(newComic.getDescription())
                    .isFinished(false)
                    .isDeleted(false)
                    .thumbnailImage(newComic.getThumbnailImage().getBytes())
                    .build();

            return comicRepository.save(comic);

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public byte[] getThumbnailImage(Long id) {
        ComicEntity comic = comicRepository.findById(id).get();
        return comic.getThumbnailImage();
    }
}
