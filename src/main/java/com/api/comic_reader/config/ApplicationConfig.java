package com.api.comic_reader.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.enums.Role;
import com.api.comic_reader.repositories.UserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                UserEntity admin = UserEntity.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123456"))
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
