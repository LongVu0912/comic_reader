package com.api.comic_reader.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.api.comic_reader.chatgpt.ChatGptRequest;
import com.api.comic_reader.chatgpt.ChatGptResponse;
import com.api.comic_reader.config.EnvVariables;
import com.api.comic_reader.dtos.requests.AskGptRequest;
import com.api.comic_reader.dtos.requests.ComicsAskGptRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicGenreResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
import com.api.comic_reader.services.ComicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatGptController {
    @Autowired
    private ComicService comicService;

    private final String chatGptApiUrl = EnvVariables.chatGptApiUrl;
    private final String chatGptModel = EnvVariables.chatGptModel;

    private final String lmStudioApiUrl = EnvVariables.lmStudioApiUrl;
    private final String lmStudioModel = EnvVariables.lmStudioModel;
    private final String modelPrompt = EnvVariables.modelPrompt;

    private static final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("null")
    @PostMapping("/findComics")
    public ResponseEntity<ApiResponse> findComics(@RequestBody AskGptRequest askGPTRequest) throws AppException {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicsAskGptRequest> comicsAskGptRequests = comics.stream()
                .map(comic -> ComicsAskGptRequest.builder()
                        .name(comic.getName())
                        .description(comic.getDescription())
                        .genres(comic.getGenres().stream()
                                .map(ComicGenreResponse::getName)
                                .toList())
                        .build())
                .toList();

        ObjectMapper objectMapper = new ObjectMapper();
        String comicsAskGptRequestsJson = "";
        try {
            comicsAskGptRequestsJson = objectMapper.writeValueAsString(comicsAskGptRequests);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String question = askGPTRequest.getQuestion() + " trong các truyện sau đây, dựa trên thể loại và nội dung: "
                + comicsAskGptRequestsJson + " .Chỉ đưa ra tên truyện";

        ChatGptRequest request = new ChatGptRequest(chatGptModel, question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + askGPTRequest.getKey());
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGptResponse> responseEntity =
                restTemplate.exchange(chatGptApiUrl, HttpMethod.POST, entity, ChatGptResponse.class);

        ChatGptResponse chatGptResponse = responseEntity.getBody();

        System.out.println(chatGptResponse.getChoices().get(0).getMessage().getContent());
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Ask GPT successfully")
                        .result(chatGptResponse.getChoices().get(0).getMessage().getContent())
                        .build());
    }

    @SuppressWarnings("null")
    @PostMapping("/askGpt")
    public ResponseEntity<ApiResponse> askGpt(@RequestBody AskGptRequest askGPTRequest) throws AppException {
        String question = askGPTRequest.getQuestion();

        ChatGptRequest request = new ChatGptRequest(chatGptModel, question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + askGPTRequest.getKey());
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGptResponse> responseEntity =
                restTemplate.exchange(chatGptApiUrl, HttpMethod.POST, entity, ChatGptResponse.class);

        ChatGptResponse chatGptResponse = responseEntity.getBody();

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Ask GPT successfully")
                        .result(chatGptResponse.getChoices().get(0).getMessage().getContent())
                        .build());
    }

    @PostMapping("/askLMStudio")
    public ResponseEntity<ApiResponse> askAI(@RequestBody AskGptRequest askGptRequest)
            throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("model", lmStudioModel);
        body.put("temperature", 0.8);
        body.put("max_tokens", -1);
        body.put("stream", false);

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", modelPrompt);
        messages.add(systemMessage);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", askGptRequest.getQuestion());
        messages.add(userMessage);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(lmStudioApiUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            throw new AppException(ErrorCode.NO_AI_FUNCTION);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());

        String content =
                rootNode.path("choices").get(0).path("message").path("content").asText();

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Ask AI successfully")
                        .result(content)
                        .build());
    }
}
