package com.api.comic_reader.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    // The index of the choice
    private int index;
    // The message of the choice
    private Message message;
}
