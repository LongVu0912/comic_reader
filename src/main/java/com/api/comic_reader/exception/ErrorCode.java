package com.api.comic_reader.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(8888, "Access denied, you don't have permission", HttpStatus.OK),
    WRONG_EMAIL_OR_PASSWORD(4000, "Wrong username or password", HttpStatus.OK),
    TOKEN_IS_REQUIRED(4001, "Token is required", HttpStatus.OK),
    INVALID_TOKEN(4002, "Token is invalid", HttpStatus.OK),
    EMAIL_TAKEN(4003, "Email is already taken", HttpStatus.OK),

    USER_NOT_FOUND(4004, "User not found", HttpStatus.OK),
    COMIC_NOT_FOUND(4004, "Comic not found", HttpStatus.OK),
    COMIC_CHAPTERS_NOT_FOUND(4004, "Comic's chapters not found", HttpStatus.OK),
    CHAPTER_NOT_FOUND(4004, "Chapter not found", HttpStatus.OK),
    CHAPTER_IMAGES_NOT_FOUND(4004, "Chapter's images not found", HttpStatus.OK),
    IMAGE_NOT_FOUND(4004, "Image not found", HttpStatus.OK),

    THUMBNAIL_INVALID(4009, "Thumbnail is invalid", HttpStatus.OK),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
