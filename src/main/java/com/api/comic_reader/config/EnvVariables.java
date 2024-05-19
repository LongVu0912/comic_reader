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

    // ChatGPT
    public final String chatGptApiUrl = "https://api.openai.com/v1/chat/completions";
    public final String chatGptModel = "gpt-3.5-turbo";

    // LMStudio
    public final String lmStudioApiUrl = "http://localhost:8088/v1/chat/completions";
    public final String lmStudioModel = "duyntnet/Vistral-7B-Chat-DPO-imatrix-GGUF";
    public final String modelPrompt = "Bạn là trợ lý AI truyệt vời. Hãy trả lời ngắn gọn nhé";
}
