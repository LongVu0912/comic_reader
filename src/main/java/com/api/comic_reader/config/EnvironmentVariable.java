package com.api.comic_reader.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvironmentVariable {
    public String jwtSignerKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public Long jwtExpiration = (long) (60 * 60 * 1000); // 1 hour
    public String passwordEncoderKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public String baseUrl = "http://localhost:8080";
}
