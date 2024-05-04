package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.dtos.responses.GenreResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.GenreEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.GenreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class GenreService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreResponse> getAllGenres() {
        List<GenreEntity> genres = genreRepository.findAll();

        List<GenreResponse> genreResponses = genres.stream()
                .map(genre -> {
                    return GenreResponse.builder()
                            .id(genre.getId())
                            .name(genre.getName())
                            .genreDescription(genre.getGenreDescription())
                            .build();
                })
                .collect(Collectors.toList());

        return genreResponses;
    }

    public GenreEntity getGenreById(Long genreId) {
        Optional<GenreEntity> genreOptional = genreRepository.findById(genreId);

        if (!genreOptional.isPresent()) {
            throw new AppException(ErrorCode.GENRE_NOT_FOUND);
        }

        return genreOptional.get();
    }

    public List<ComicResponse> getComicsByGenre(Long genreId) {
        return null;
    }

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
