package com.api.comic_reader.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PasswordEncoderConfig {
    // This is the key used for encoding passwords
    @Value("${app.encoder-key}")
    private String ENCODE_KEY;

    // This method creates a BCryptPasswordEncoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Create a SecureRandom instance with the encode key
        SecureRandom secureRandom = new SecureRandom(ENCODE_KEY.getBytes());
        // Return a BCryptPasswordEncoder instance with a strength of 10 and the SecureRandom instance
        return new BCryptPasswordEncoder(10, secureRandom);
    }
}
