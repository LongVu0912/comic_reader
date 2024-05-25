package com.api.comic_reader.services;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.RatingRequest;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.entities.RatingEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ComicRepository;
import com.api.comic_reader.repositories.RatingRepository;
import com.api.comic_reader.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity()
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public void rateComic(RatingRequest ratingRequest) throws AppException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // Check if user exists
        Optional<UserEntity> user = userRepository.findByUsername(name);

        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        // Check if comic exists
        Optional<ComicEntity> comic = comicRepository.findById(ratingRequest.getComicId());

        if (comic.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        // Check if user has rated this comic before
        if (!ratingRepository.existsByComicAndUser(comic.get(), user.get())) {
            ratingRepository.save(RatingEntity.builder()
                    .comic(comic.get())
                    .user(user.get())
                    .score(ratingRequest.getScore())
                    .build());
        } else {
            ratingRepository.updateByComicAndUser(comic.get(), user.get(), ratingRequest.getScore());
        }
    }

    public Double getComicAverageRating(Long comicId) throws AppException {
        Optional<ComicEntity> comic = comicRepository.findById(comicId);

        if (comic.isEmpty()) {
            throw new AppException(ErrorCode.COMIC_NOT_FOUND);
        }

        Double averageRating = ratingRepository.getComicAverageRating(comic.get());

        if (averageRating == null) {
            return 0D;
        }

        return averageRating;
    }
}
