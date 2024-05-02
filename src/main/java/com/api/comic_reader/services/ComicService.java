package com.api.comic_reader.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.api.comic_reader.config.EnvVariables;
import com.api.comic_reader.dtos.requests.ComicRequest;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
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
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterService chapterService;

    public List<ComicResponse> getAllComics() {
        List<ComicEntity> comics = comicRepository.findAll();

        if (comics.isEmpty()) {
            return Collections.emptyList();
        }

        return comics.stream()
                .map(comic -> {
                    String thumbnailUrl = EnvVariables.baseUrl + "/api/comic/thumbnail/" + comic.getId();
                    ChapterResponse lastestChapter = chapterService.getLastestChapter(comic.getId());

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

                    return ComicResponse.builder()
                            .id(comic.getId())
                            .name(comic.getName())
                            .author(comic.getAuthor())
                            .description(comic.getDescription())
                            .thumbnailUrl(thumbnailUrl)
                            .view(comic.getView())
                            .lastestChapter(lastestChapter)
                            .isDeleted(comic.getIsDeleted())
                            .isFinished(comic.getIsFinished())
                            .chapters(null)
                            .genres(genreResponses)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ComicEntity insertComic(ComicRequest newComic) throws AppException {

        String originalFilename = newComic.getThumbnailImage().getOriginalFilename();
        if (originalFilename == null) {
            throw new AppException(ErrorCode.INVALID_THUMBNAIL);
        }

        String fileName = StringUtils.cleanPath(originalFilename);
        if (fileName.contains("..")) {
            throw new AppException(ErrorCode.INVALID_THUMBNAIL);
        }

        Optional<ComicEntity> comicOptional = comicRepository.findByName(newComic.getName());
        if (comicOptional.isPresent()) {
            throw new AppException(ErrorCode.COMIC_NAME_TAKEN);
        }

        try {
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

    public byte[] getThumbnailImage(Long comicId) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);
        if (comicOptional.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }
        ComicEntity comic = comicOptional.get();
        return comic.getThumbnailImage();
    }

    public List<ComicResponse> searchComics(String keyword) throws AppException {
        if (keyword == null || keyword.length() < EnvVariables.minSearchKeywordLength) {
            throw new AppException(ErrorCode.INVALID_KEYWORD);
        }
        List<ComicEntity> comics = comicRepository.findByNameContainingIgnoreCase(keyword);

        if (comics.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        return comics.stream()
                .map(comic -> {
                    String thumbnailUrl = "/api/comic/thumbnail/" + comic.getId();
                    ChapterResponse lastestChapter = chapterService.getLastestChapter(comic.getId());

                    return ComicResponse.builder()
                            .id(comic.getId())
                            .name(comic.getName())
                            .author(comic.getAuthor())
                            .description(comic.getDescription())
                            .thumbnailUrl(thumbnailUrl)
                            .view(comic.getView())
                            .lastestChapter(lastestChapter)
                            .isDeleted(comic.getIsDeleted())
                            .isFinished(comic.getIsFinished())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
