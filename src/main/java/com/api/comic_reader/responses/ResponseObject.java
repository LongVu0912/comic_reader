package com.api.comic_reader.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseObject {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("data")
    private Object data;

}
