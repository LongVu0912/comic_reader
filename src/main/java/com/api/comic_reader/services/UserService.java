package com.api.comic_reader.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.ChangeInformationRequest;
import com.api.comic_reader.dtos.requests.ChangePasswordRequest;
import com.api.comic_reader.dtos.responses.UserResponse;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.repositories.UserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getAllUsers() throws AppException {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .dateOfBirth(DateUtil.convertDateToString(user.getDateOfBirth()))
                        .isMale(user.getIsMale())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    @PostAuthorize("hasAuthority('SCOPE_ADMIN') or returnObject.username == authentication.name")
    public UserEntity getUserInformationById(Long id) throws AppException {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userOptional.get();
    }

    public UserResponse getMyInformation() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(name);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return UserResponse.builder()
                .id(userOptional.get().getId())
                .username(userOptional.get().getUsername())
                .email(userOptional.get().getEmail())
                .fullName(userOptional.get().getFullName())
                .dateOfBirth(DateUtil.convertDateToString(userOptional.get().getDateOfBirth()))
                .isMale(userOptional.get().getIsMale())
                .build();
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) throws AppException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(name);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        UserEntity user = userOptional.get();
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    public void changeInformation(ChangeInformationRequest changeInformationRequest) throws AppException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(name);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        UserEntity user = userOptional.get();
        user.setFullName(changeInformationRequest.getFullName());
        user.setDateOfBirth(DateUtil.convertStringToDate(changeInformationRequest.getDateOfBirth()));
        user.setIsMale(changeInformationRequest.getIsMale());
        userRepository.save(user);
    }
}
