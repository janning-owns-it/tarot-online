package com.janning_owns_it.tarot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(Map.of("error", exception.getMessage()));
    }
}
