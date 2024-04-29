package com.api.comic_reader.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvVariables {
    public final String jwtSignerKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public final Long jwtExpiration = (long) (24 * 60 * 60 * 1000); // 1 day
    public final String passwordEncoderKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public final String baseUrl = "https://comic.pantech.vn";
    public final Long minSearchKeywordLength = 4L;
    public final Long minCommentLength = 8L;
}
