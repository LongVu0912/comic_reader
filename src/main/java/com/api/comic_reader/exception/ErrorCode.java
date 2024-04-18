package com.api.comic_reader.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_EMAIL_OR_PASSWORD(4000, "Wrong email or password", HttpStatus.BAD_REQUEST),
    TOKEN_IS_REQUIRED(4001, "Token is required", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(4002, "Token is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_TAKEN(4003, "Email is already taken", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4004, "User not found", HttpStatus.BAD_REQUEST),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
