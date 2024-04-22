package com.api.comic_reader.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PasswordEncoderConfig {

    private final String ENCODE_KEY = EnvironmentVariable.passwordEncoderKey;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        SecureRandom secureRandom = new SecureRandom(ENCODE_KEY.getBytes());
        return new BCryptPasswordEncoder(10, secureRandom);
    }
}
