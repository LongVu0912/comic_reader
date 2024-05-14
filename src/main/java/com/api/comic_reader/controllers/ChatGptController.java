package com.api.comic_reader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.api.comic_reader.chatgpt.ChatGptRequest;
import com.api.comic_reader.chatgpt.ChatGptResponse;
import com.api.comic_reader.dtos.requests.AskGptRequest;
import com.api.comic_reader.dtos.requests.ComicsAskGptRequest;
import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.dtos.responses.ComicResponse;
import com.api.comic_reader.services.ComicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatGptController {
    @Autowired
    private ComicService comicService;

    String apiUrl = "https://api.openai.com/v1/chat/completions";
    String model = "gpt-3.5-turbo";

    private static RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("null")
    @GetMapping("/findComics")
    public ResponseEntity<ApiResponse> findComics(@RequestBody AskGptRequest askGPTRequest) {
        List<ComicResponse> comics = comicService.getAllComics();

        List<ComicsAskGptRequest> comicsAskGptRequests = comics.stream()
                .map(comic -> {
                    return ComicsAskGptRequest.builder()
                            .name(comic.getName())
                            .description(comic.getDescription())
                            .genres(comic.getGenres().stream()
                                    .map(genre -> genre.getName())
                                    .toList())
                            .build();
                })
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

        ChatGptRequest request = new ChatGptRequest(model, question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + askGPTRequest.getKey());
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGptResponse> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ChatGptResponse.class);

        ChatGptResponse chatGptResponse = responseEntity.getBody();

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Ask GPT successfully")
                        .result(chatGptResponse.getChoices().get(0).getMessage().getContent())
                        .build());
    }

    @SuppressWarnings("null")
    @GetMapping("/ask")
    public ResponseEntity<ApiResponse> askGpt(@RequestBody AskGptRequest askGPTRequest) {
        String question = askGPTRequest.getQuestion();

        ChatGptRequest request = new ChatGptRequest(model, question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + askGPTRequest.getKey());
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGptResponse> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ChatGptResponse.class);

        ChatGptResponse chatGptResponse = responseEntity.getBody();

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Ask GPT successfully")
                        .result(chatGptResponse.getChoices().get(0).getMessage().getContent())
                        .build());
    }
}
