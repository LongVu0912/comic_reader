package com.api.comic_reader.services;

import com.api.comic_reader.dtos.requests.RegisterRequest;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.enums.Role;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class ComicUserService {
    @Autowired
    private ComicUserRepository comicUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<ComicUserEntity> getAllUsers() throws Exception {
        List<ComicUserEntity> comicUsers = null;
        try {
            comicUsers = comicUserRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return comicUsers;
    }

    public ComicUserEntity register(RegisterRequest newComicUser) throws AppException {
        ComicUserEntity comicUser = null;
        Optional<ComicUserEntity> comicUserOptional = comicUserRepository.findByEmail(newComicUser.getEmail());
        if (comicUserOptional.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        try {
            comicUser = ComicUserEntity.builder()
                    .email(newComicUser.getEmail())
                    .password(passwordEncoder.encode(newComicUser.getPassword()))
                    .fullName(newComicUser.getFullName())
                    .dateOfBirth(DateUtil.convertStringToDate(newComicUser.getDateOfBirth()))
                    .isMale(newComicUser.getIsMale())
                    .isBanned(false)
                    .role(Role.USER)
                    .build();
            comicUserRepository.save(comicUser);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return comicUser;
    }

    @PostAuthorize("hasAuthority('SCOPE_ADMIN') or returnObject.email == authentication.name")
    public ComicUserEntity getUserInformationById(Long id) throws AppException {
        Optional<ComicUserEntity> comicUserOptional = comicUserRepository.findById(id);
        if (comicUserOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        ComicUserEntity comicUser = comicUserOptional.get();
        return comicUser;
    }
}
