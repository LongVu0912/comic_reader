package com.api.comic_reader.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.api.comic_reader.dtos.requests.ComicRequest;
import com.api.comic_reader.dtos.responses.ChapterResponse;
import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.dtos.responses.ComicInformationResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.RatingEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.BookmarkRepository;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.RatingRepository;
import com.api.comic_reader.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity()
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Value("${app.base-url}")
    private String BASE_URL;

    public List<ComicResponse> getAllComics() {
        List<ComicEntity> comics = comicRepository.findAll();

        if (comics.isEmpty()) {
            return Collections.emptyList();
        }

        return comics.stream()
                .filter(comic -> !comic.getIsDeleted())
                .map(comic -> {
                    String thumbnailUrl = BASE_URL + "/api/comic/thumbnail/" + comic.getId();
                    ChapterResponse lastChapter = chapterService.getLastChapter(comic.getId());

                    List<ComicGenreResponse> genreResponses = genreService.getComicGenres(comic.getId());

                    return ComicResponse.builder()
                            .id(comic.getId())
                            .name(comic.getName())
                            .author(comic.getAuthor())
                            .description(comic.getDescription())
                            .thumbnailUrl(thumbnailUrl)
                            .view(comic.getView())
                            .lastChapter(lastChapter)
                            .isFinished(comic.getIsFinished())
                            .genres(genreResponses)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void insertComic(ComicRequest newComic) throws AppException {

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

            comicRepository.save(comic);

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public byte[] getThumbnailImage(Long comicId) {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);
        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }
        ComicEntity comic = comicOptional.get();
        return comic.getThumbnailImage();
    }

    public List<ComicResponse> searchComics(String keyword) throws AppException {
        if (keyword == null || keyword.length() < 4L) {
            throw new AppException(ErrorCode.INVALID_KEYWORD);
        }
        List<ComicEntity> comics = comicRepository.findByNameContainingIgnoreCase(keyword);

        if (comics.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

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
                            .genres(genreService.getComicGenres(comic.getId()))
                            .isFinished(comic.getIsFinished())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public ComicInformationResponse getComicInformation(Long comicId) {
        // Find comic by id
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);
        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }
        ComicEntity comic = comicOptional.get();

        // Get genres of comic
        List<ComicGenreResponse> genres = genreService.getComicGenres(comicId);

        // Get user rating score of comic
        Long userRatingScore = null;

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Optional<UserEntity> userOptional = userRepository.findByUsername(name);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            RatingEntity rating = ratingRepository.findByComicAndUser(comic, user);

            if (rating != null) {
                userRatingScore = rating.getScore();
            }
        }

        return ComicInformationResponse.builder()
                .id(comic.getId())
                .name(comic.getName())
                .author(comic.getAuthor())
                .description(comic.getDescription())
                .thumbnailUrl(BASE_URL + "/api/comic/thumbnail/" + comic.getId())
                .view(comic.getView())
                .isFinished(comic.getIsFinished())
                .userRatingScore(userRatingScore)
                .averageRatingScore(ratingService.getComicAverageRating(comicId))
                .genres(genres)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void editComic(Long comicId, ComicRequest editComicRequest) throws AppException {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);
        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();

        if (editComicRequest.getName() != null) {
            comic.setName(editComicRequest.getName());
        }
        if (editComicRequest.getAuthor() != null) {
            comic.setAuthor(editComicRequest.getAuthor());
        }
        if (editComicRequest.getDescription() != null) {
            comic.setDescription(editComicRequest.getDescription());
        }
        if (editComicRequest.getThumbnailImage() != null) {
            try {
                String originalFilename = editComicRequest.getThumbnailImage().getOriginalFilename();
                if (originalFilename == null) {
                    throw new AppException(ErrorCode.INVALID_THUMBNAIL);
                }

                String fileName = StringUtils.cleanPath(originalFilename);
                if (fileName.contains("..")) {
                    throw new AppException(ErrorCode.INVALID_THUMBNAIL);
                }
                comic.setThumbnailImage(editComicRequest.getThumbnailImage().getBytes());
            } catch (Exception e) {
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }

        comicRepository.save(comic);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteComic(Long comicId) throws AppException {
        Optional<ComicEntity> comicOptional = comicRepository.findById(comicId);
        if (comicOptional.isEmpty() || comicOptional.get().getIsDeleted()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        ComicEntity comic = comicOptional.get();
        comic.setIsDeleted(true);

        bookmarkRepository.deleteByComic(comic);
        comicRepository.save(comic);
    }
}
