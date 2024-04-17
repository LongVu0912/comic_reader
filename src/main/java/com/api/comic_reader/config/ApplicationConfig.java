package com.api.comic_reader.config;

import java.security.SecureRandom;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.api.comic_reader.enums.Role;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.utils.DateUtil;
import com.api.comic_reader.entities.ComicUserEntity;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ComicUserRepository userRepository;
    private final String ENCODE_KEY = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        SecureRandom secureRandom = new SecureRandom(ENCODE_KEY.getBytes());
        return new BCryptPasswordEncoder(10, secureRandom);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                ComicUserEntity admin = ComicUserEntity.builder()
                        .email("admin@gmail.com")
                        .password("123456")
                        .fullName("Admin")
                        .dateOfBirth(DateUtil.convertStringToDate("1-1-2000"))
                        .isMale(true)
                        .isBanned(false)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
