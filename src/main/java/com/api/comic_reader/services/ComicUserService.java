package com.api.comic_reader.services;

import com.api.comic_reader.dtos.LoginDTO;
import com.api.comic_reader.dtos.RegisterDTO;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.entities.RoleEntity;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.repositories.RoleRepository;
import com.api.comic_reader.responses.LoginResponse;
import com.api.comic_reader.utils.DateUtil;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComicUserService {
    @Autowired
    private ComicUserRepository comicUserRepository;
    private final RoleRepository roleRepository;
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

    public ComicUserEntity register(RegisterDTO newComicUser) throws Exception {
        ComicUserEntity comicUser = null;
        RoleEntity role = roleRepository.findByName("USER");
        if (comicUserRepository.findByEmail(newComicUser.getEmail()) != null) {
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
                    .roles(Collections.singleton(role))
                    .build();
            comicUserRepository.save(comicUser);
        } catch (Exception e) {
            throw new Exception("Can not register a new user, please try again!");
        }
        return comicUser;
    }

    public LoginResponse login(LoginDTO loginDTO) throws Exception {
        LoginResponse loginResponse = null;
        try {
            ComicUserEntity comicUser = comicUserRepository.findByEmail(loginDTO.getEmail());
            if (comicUser != null && !passwordEncoder.matches(loginDTO.getPassword(), comicUser.getPassword())) {
                loginResponse = LoginResponse.builder()
                        .username(comicUser.getEmail())
                        .build();
            }
            
        } catch (Exception e) {
            throw new Exception("Can not login, please try again!");
        }
        if (loginResponse == null) {
            throw new Exception("Email or password is incorrect, please try again!");        
        }
        return loginResponse;
    }
}
