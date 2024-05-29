package com.api.comic_reader.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    // Role of the entity sending the message, e.g., "user", "assistant", etc.
    private String role;
    // The actual content of the message
    private String content;
}
