package com.api.comic_reader.exception;

import lombok.Getter;
import lombok.Setter;

@Getter // This annotation is used to generate getters for all fields.
@Setter // This annotation is used to generate setters for all fields.
public class AppException extends RuntimeException {

    // This field stores the error code associated with the exception.
    private final ErrorCode errorCode;

    // This constructor initializes the exception with a specific error code.
    // The message of the exception is set to the message of the error code.
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
