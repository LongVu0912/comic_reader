package com.api.comic_reader.chatgpt;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatGptRequest {
    // The model to be used for the chat
    private String model;
    // The list of messages in the chat
    private List<Message> messages = new ArrayList<>();

    // This constructor initializes the model and adds the first message to the chat
    public ChatGptRequest(String model, String query) {
        this.model = model;
        this.messages.add(new Message("user", query));
    }
}
