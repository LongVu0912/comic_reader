package com.api.comic_reader.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvVariables {
    public final String jwtSignerKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public final Long jwtExpiration = (long) (24 * 60 * 60 * 1000); // 1 day
    public final String passwordEncoderKey = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";
    public final String baseUrl = "http://comic.pantech.vn:8080";
    public final Long minSearchKeywordLength = 4L;
    public final Long minCommentLength = 8L;

    // ChatGpt
    public final String chatGptApiUrl = "https://api.openai.com/v1/chat/completion";
    public final String chatGptModel = "gpt-3.5-turbo";

    // LM Studio
    public final String lmStudioApiUrl = "http://localhost:8088/v1/chat/completions";
    public final String lmStudioModel = "janhq/Vistral-7b-Chat-GGUF";
    public final String modelPrompt = "Always answer in funny style.";
}
