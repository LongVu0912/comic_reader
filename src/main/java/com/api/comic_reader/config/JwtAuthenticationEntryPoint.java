package com.api.comic_reader.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.api.comic_reader.dtos.responses.ApiResponse;
import com.api.comic_reader.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // This method is invoked when an unauthenticated user tries to access a secured HTTP resource
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.INVALID_TOKEN;

        // Set the HTTP status code to the value of the error code
        response.setStatus(errorCode.getStatusCode().value());
        // Set the content type of the response to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Create an ApiResponse object with the error code and message
        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        // Write the ApiResponse object to the response body as a JSON string
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        // Flush the response buffer to ensure that the response is sent
        response.flushBuffer();
    }
}
