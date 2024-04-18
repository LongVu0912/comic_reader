package com.api.comic_reader.services;

import com.api.comic_reader.dtos.requests.RegisterRequest;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.enums.Role;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@EnableMethodSecurity(prePostEnabled = true)
public class ComicUserService {
    @Autowired
    private ComicUserRepository comicUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<ComicUserEntity> getAllUsers() {
        List<ComicUserEntity> comicUsers = null;
        try {
            comicUsers = comicUserRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comicUsers;
    }

    public ComicUserEntity register(RegisterRequest newComicUser) throws Exception {
        ComicUserEntity comicUser = null;
        Optional<ComicUserEntity> comicUserOptional = comicUserRepository.findByEmail(newComicUser.getEmail());
        if (comicUserOptional.isPresent()) {
            throw new Exception("Email is already taken, please use another email!");
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
            throw new Exception("Can not register a new user, please try again!");
        }
        return comicUser;
    }

    @PostAuthorize("hasAuthority('SCOPE_ADMIN') or returnObject.email == authentication.name")
    public ComicUserEntity getUserInformationById(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: {}", authentication.getName());
        Optional<ComicUserEntity> comicUserOptional = comicUserRepository.findById(id);
        if (comicUserOptional.isEmpty()) {
            return null;
        }
        ComicUserEntity comicUser = comicUserOptional.get();
        return comicUser;
    }
}
