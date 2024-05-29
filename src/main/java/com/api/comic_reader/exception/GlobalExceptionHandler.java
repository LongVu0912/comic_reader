package com.api.comic_reader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.comic_reader.dtos.responses.ApiResponse;

@ControllerAdvice // This annotation is used to define @ExceptionHandler, @InitBinder, and @ModelAttribute methods that
// apply to all @RequestMapping methods.
public class GlobalExceptionHandler {

    // This method handles all RuntimeExceptions that are not caught elsewhere.
    // It returns a ResponseEntity with a status of 400 and an ApiResponse containing the error code and message.
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // This method handles all Exceptions that are not caught elsewhere.
    // It returns a ResponseEntity with a status of 400 and an ApiResponse containing the error code and message.
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // This method handles all AppExceptions.
    // It returns a ResponseEntity with a status code and an ApiResponse containing the error code and message from the
    // AppException.
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // This method handles all AccessDeniedExceptions.
    // It returns a ResponseEntity with a status code and an ApiResponse containing the error code and message for
    // access denied.
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    // This method handles all MethodArgumentNotValidExceptions (validation errors).
    // It returns a ResponseEntity with a status of 200 and an ApiResponse containing the error code and validation
    // error message.
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        @SuppressWarnings("null")
        String message = exception.getFieldError().getDefaultMessage();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .code(ErrorCode.EXISTS_CODE.getCode())
                        .message(message)
                        .build());
    }
}
