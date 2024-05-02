package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.GenreEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ComicRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class GenreService {
    @Autowired
    private ComicRepository comicRepository;

    public List<ComicGenreResponse> getComicGenres(Long comicId) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (!comicOptional.isPresent()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        List<ComicGenreEntity> genres = comic.getGenres();

        List<GenreEntity> genreEntities =
                genres.stream().map(ComicGenreEntity::getGenre).collect(Collectors.toList());

        List<ComicGenreResponse> genreResponses = genreEntities.stream()
                .map(genre -> {
                    return ComicGenreResponse.builder()
                            .id(genre.getId())
                            .name(genre.getName())
                            .build();
                })
                .collect(Collectors.toList());

        return genreResponses;
    }
}
