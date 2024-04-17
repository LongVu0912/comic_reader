package com.api.comic_reader.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseObject {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

}
