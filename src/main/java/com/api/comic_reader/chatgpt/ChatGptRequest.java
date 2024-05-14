package com.api.comic_reader.chatgpt;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatGptRequest {
    private String model;
    private List<Message> messages = new ArrayList<>();

    public ChatGptRequest(String model, String query) {
        this.model = model;
        this.messages.add(new Message("user", query));
    }
}
