package com.api.comic_reader.chatgpt;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatGptResponse {
    // The list of choices in the response
    private List<Choice> choices;
}
