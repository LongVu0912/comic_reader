package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.AddGenresToComicRequest;
import com.api.comic_reader.dtos.requests.AddNewGenreRequest;
import com.api.comic_reader.dtos.requests.FilterGenresRequest;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.dtos.responses.GenreResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.ComicGenreEntity;
import com.api.comic_reader.entities.GenreEntity;
import com.api.comic_reader.entities.composite_keys.ComicGenreKey;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ComicGenreRepository;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.GenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity()
public class GenreService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ComicGenreRepository comicGenreRepository;

    @Value("${app.base-url}")
    private String BASE_URL;

    // This method returns all genres in the database.
    public List<GenreResponse> getAllGenres() {
        List<GenreEntity> genres = genreRepository.findAll();

        return genres.stream()
                .map(genre -> GenreResponse.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .genreDescription(genre.getGenreDescription())
                        .build())
                .collect(Collectors.toList());
    }

    // This method returns all genres of a comic with the given ID.
    // It throws an exception if the comic is not found or if it is deleted.
    public List<ComicGenreResponse> getComicGenres(Long comicId) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        List<ComicGenreEntity> genres = comic.getGenres();

        List<GenreEntity> genreEntities =
                genres.stream().map(ComicGenreEntity::getGenre).toList();

        return genreEntities.stream()
                .map(genre -> ComicGenreResponse.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .collect(Collectors.toList());
    }

    // This method returns all comics that have all of the genres specified in the request.
    public List<ComicResponse> getComicsByGenres(FilterGenresRequest genresRequest) {
        List<Long> genreIds = genresRequest.getGenreIds();
        List<ComicEntity> comics = comicRepository.findByGenresIdIn(genreIds, genreIds.size());

        return comics.stream()
                .filter(comic -> !comic.getIsDeleted())
                .map(comic -> {
                    String thumbnailUrl = BASE_URL + "/api/comic/thumbnail/" + comic.getId();
                    ChapterResponse lastChapter = chapterService.getLastChapter(comic.getId());

                    return ComicResponse.builder()
                            .id(comic.getId())
                            .name(comic.getName())
                            .author(comic.getAuthor())
                            .description(comic.getDescription())
                            .thumbnailUrl(thumbnailUrl)
                            .view(comic.getView())
                            .lastChapter(lastChapter)
                            .genres(this.getComicGenres(comic.getId()))
                            .isFinished(comic.getIsFinished())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // This method adds a new genre to the database.
    // It requires the user to have ADMIN authority.
    // It throws an exception if a genre with the same name already exists.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void addNewGenre(AddNewGenreRequest newGenre) {
        try {
            genreRepository.save(GenreEntity.builder()
                    .name(newGenre.getName())
                    .genreDescription(newGenre.getGenreDescription())
                    .build());
        } catch (Exception e) {
            throw new AppException(ErrorCode.GENRE_EXISTS);
        }
    }

    // This method adds genres to a comic.
    // It requires the user to have ADMIN authority.
    // It throws an exception if the comic is not found or if it is deleted.
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void addGenresToComic(AddGenresToComicRequest addGenresRequest) {
        Long comicId = addGenresRequest.getComicId();
        List<Long> genresId = addGenresRequest.getGenreIds();

        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);

        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        List<GenreEntity> genres = genreRepository.findAllById(genresId);

        for (GenreEntity genre : genres) {
            if (comic.getGenres().stream()
                    .anyMatch(comicGenres -> comicGenres.getGenre().getId().equals(genre.getId()))) {
                continue;
            }

            ComicGenreKey comicGenreKey = new ComicGenreKey();
            comicGenreKey.setComicId(comic.getId());
            comicGenreKey.setGenreId(genre.getId());

            comicGenreRepository.save(ComicGenreEntity.builder()
                    .id(comicGenreKey)
                    .comic(comic)
                    .genre(genre)
                    .build());
        }
    }
}
