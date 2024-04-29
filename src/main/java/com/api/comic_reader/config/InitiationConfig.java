package com.api.comic_reader.config;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.api.comic_reader.entities.GenreEntity;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.enums.Role;
import com.api.comic_reader.repositories.GenreRepository;
import com.api.comic_reader.repositories.UserRepository;
import com.api.comic_reader.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class InitiationConfig {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;

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
            if (genreRepository.findAll().isEmpty()) {
                genreRepository.saveAll(List.of(
                        GenreEntity.builder()
                                .name("Action")
                                .genreDescription("Heroes and villains in high-energy conflicts.")
                                .build(),
                        GenreEntity.builder()
                                .name("Adventure")
                                .genreDescription("Journeys through exotic, perilous settings.")
                                .build(),
                        GenreEntity.builder()
                                .name("Comedy")
                                .genreDescription("Humorous stories designed to amuse.")
                                .build(),
                        GenreEntity.builder()
                                .name("Drama")
                                .genreDescription("Emotional storytelling with complex themes.")
                                .build(),
                        GenreEntity.builder()
                                .name("Fantasy")
                                .genreDescription("Magical worlds with mythical beings.")
                                .build(),
                        GenreEntity.builder()
                                .name("Horror")
                                .genreDescription("Suspenseful tales with supernatural elements.")
                                .build(),
                        GenreEntity.builder()
                                .name("Kid")
                                .genreDescription("Fun, educational content for young readers.")
                                .build(),
                        GenreEntity.builder()
                                .name("Romance")
                                .genreDescription("Stories about love and relationships.")
                                .build(),
                        GenreEntity.builder()
                                .name("Sport")
                                .genreDescription("The excitement and drama of athletic events.")
                                .build(),
                        GenreEntity.builder()
                                .name("Tragedy")
                                .genreDescription("Dark, somber narratives with reflective endings.")
                                .build()));
            }
        };
    }
}
