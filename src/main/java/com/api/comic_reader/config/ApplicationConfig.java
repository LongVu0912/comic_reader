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
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Admin")
                        .dateOfBirth(DateUtil.getCurrentDate())
                        .isMale(true)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
            }
            if (userRepository.findByEmail("user@gmail.com").isEmpty()) {

                UserEntity user = UserEntity.builder()
                        .username("user")
                        .email("user@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("User")
                        .dateOfBirth(DateUtil.getCurrentDate())
                        .isMale(true)
                        .role(Role.USER)
                        .build();

                userRepository.save(user);
            }
        };
    }
}
