package com.api.comic_reader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter // This annotation is used to generate getters for all fields.
public enum ErrorCode {
    // Each enum value represents a specific error that can occur in the application.
    // Each error has a code, a message, and an HTTP status code associated with it.

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    ACCESS_DENIED(8888, "Access denied, you don't have permission", HttpStatus.OK),
    WRONG_USERNAME_OR_PASSWORD(4000, "Wrong username or password", HttpStatus.OK),
    WRONG_PASSWORD(4000, "Wrong old password", HttpStatus.OK),

    TOKEN_IS_REQUIRED(4001, "Token is required", HttpStatus.OK),

    INVALID_CODE(4002, "", HttpStatus.OK),
    INVALID_TOKEN(4002, "Token is invalid", HttpStatus.OK),
    INVALID_THUMBNAIL(4002, "Thumbnail is invalid", HttpStatus.OK),
    INVALID_KEYWORD(4002, "Keyword must be larger than 3 characters", HttpStatus.OK),
    INVALID_COMMENT(4002, "Comment is invalid, comment must be larger than 7 characters", HttpStatus.OK),
    INVALID_OTP(4002, "OTP is invalid", HttpStatus.OK),

    EXISTS_CODE(4003, "", HttpStatus.OK),
    USERNAME_OR_EMAIL_TAKEN(4003, "Username or Email exists", HttpStatus.OK),
    COMIC_NAME_TAKEN(4003, "Comic name exists", HttpStatus.OK),
    BOOKMARK_EXISTS(4003, "Bookmark exists", HttpStatus.OK),
    GENRE_EXISTS(4003, "Genre exists", HttpStatus.OK),

    USER_NOT_FOUND(4004, "User not found", HttpStatus.OK),
    COMIC_NOT_FOUND(4004, "Comic not found", HttpStatus.OK),
    COMIC_CHAPTERS_NOT_FOUND(4004, "Comic's chapters not found", HttpStatus.OK),
    CHAPTER_NOT_FOUND(4004, "Chapter not found", HttpStatus.OK),
    CHAPTER_IMAGES_NOT_FOUND(4004, "Chapter's images not found", HttpStatus.OK),
    IMAGE_NOT_FOUND(4004, "Image not found", HttpStatus.OK),
    GENRE_NOT_FOUND(4004, "Genre not found", HttpStatus.OK),
    COMMENT_NOT_FOUND(4004, "Comment not found", HttpStatus.OK),

    NO_COMMENT(4005, "No comment", HttpStatus.OK),
    NO_AI_FUNCTION(4005, "AI function is turned off", HttpStatus.OK),

    ALREADY_SENT_OTP(4006, "OTP has already been sent", HttpStatus.OK),
    COMIC_ALREADY_FINISHED(4006, "Comic has already been finished", HttpStatus.OK);

    // This constructor initializes the error with a specific code, message, and HTTP status code.
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    // This field stores the error code associated with the error.
    private int code;

    // This field stores the error message associated with the error.
    private String message;

    // This field stores the HTTP status code associated with the error.
    private HttpStatusCode statusCode;
}
