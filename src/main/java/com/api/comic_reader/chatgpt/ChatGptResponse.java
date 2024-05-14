package com.api.comic_reader.chatgpt;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatGptResponse {
    private List<Choice> choices;
}
