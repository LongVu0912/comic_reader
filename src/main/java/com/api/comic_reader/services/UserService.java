package com.api.comic_reader.services;

import com.api.comic_reader.dtos.requests.RegisterRequest;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.enums.Role;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.UserRepository;
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
public class UserService {
    @Autowired
    private UserRepository comicUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() throws Exception {
        List<UserEntity> comicUsers = null;
        try {
            comicUsers = comicUserRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return comicUsers;
    }

    public UserEntity register(RegisterRequest newComicUser) throws AppException {
        UserEntity comicUser = null;
        Optional<UserEntity> comicUserOptional = comicUserRepository.findByEmail(newComicUser.getEmail());
        if (comicUserOptional.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        try {
            comicUser = UserEntity.builder()
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
    public UserEntity getUserInformationById(Long id) throws AppException {
        Optional<UserEntity> comicUserOptional = comicUserRepository.findById(id);
        if (comicUserOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        UserEntity comicUser = comicUserOptional.get();
        return comicUser;
    }
}
