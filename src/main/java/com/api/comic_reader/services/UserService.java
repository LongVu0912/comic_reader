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

    // This method is used to get all users. It requires admin authority.
    // It throws an exception if no users are found.
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

    // This method is used to get user information by ID. It requires either admin authority or the requesting user to
    // be the same as the requested user.
    // It throws an exception if the user is not found.
    @PostAuthorize("hasAuthority('SCOPE_ADMIN') or returnObject.username == authentication.name")
    public UserEntity getUserInformationById(Long id) throws AppException {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userOptional.get();
    }

    // This method is used to get the information of the currently authenticated user.
    // It throws an exception if the user is not found.
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

    // This method is used to change the password of the currently authenticated user.
    // It throws an exception if the user is not found or if the old password does not match the current password.
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

    // This method is used to change the information of the currently authenticated user.
    // It throws an exception if the user is not found.
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

    // This method is used to change the password of a user by their email.
    // It throws an exception if the user is not found.
    public void changePasswordByEmail(String email, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        UserEntity user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
