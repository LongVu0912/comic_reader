package com.api.comic_reader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    ACCESS_DENIED(8888, "Access denied, you don't have permission", HttpStatus.OK),
    WRONG_USERNAME_OR_PASSWORD(4000, "Wrong username or password", HttpStatus.OK),
    WRONG_PASSWORD(4000, "Wrong old password", HttpStatus.OK),

    TOKEN_IS_REQUIRED(4001, "Token is required", HttpStatus.OK),

    INVALID_TOKEN(4002, "Token is invalid", HttpStatus.OK),
    INVALID_THUMBNAIL(4002, "Thumbnail is invalid", HttpStatus.OK),
    INVALID_KEYWORD(4002, "Keyword must be larger than 3 characters", HttpStatus.OK),
    INVALID_COMMENT(4002, "Comment is invalid", HttpStatus.OK),

    USERNAME_OR_EMAIL_TAKEN(4003, "Email is already taken", HttpStatus.OK),
    COMIC_NAME_TAKEN(4003, "Comic name is already taken", HttpStatus.OK),
    BOOKMARK_EXISTS(4003, "Bookmark exists", HttpStatus.OK),

    USER_NOT_FOUND(4004, "User not found", HttpStatus.OK),
    COMIC_NOT_FOUND(4004, "Comic not found", HttpStatus.OK),
    COMIC_CHAPTERS_NOT_FOUND(4004, "Comic's chapters not found", HttpStatus.OK),
    CHAPTER_NOT_FOUND(4004, "Chapter not found", HttpStatus.OK),
    CHAPTER_IMAGES_NOT_FOUND(4004, "Chapter's images not found", HttpStatus.OK),
    IMAGE_NOT_FOUND(4004, "Image not found", HttpStatus.OK),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
