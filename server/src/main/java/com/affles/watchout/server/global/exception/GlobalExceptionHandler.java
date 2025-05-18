package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.common.ApiResponse;
import com.affles.watchout.server.global.status.ErrorStatus;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<?>> handleUserException(UserException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getHttpStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorStatus().getMessage(),
                        e.getErrorStatus().getHttpStatus().value(),
                        null));
    }

    @ExceptionHandler(TravelException.class)
    public ResponseEntity<ApiResponse<?>> handleTravelException(TravelException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getHttpStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorStatus().getMessage(),
                        e.getErrorStatus().getHttpStatus().value(),
                        null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(ErrorStatus._BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(
                        message,
                        ErrorStatus._BAD_REQUEST.getHttpStatus().value(),
                        null));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .status(ErrorStatus._BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(
                        e.getMessage(),
                        ErrorStatus._BAD_REQUEST.getHttpStatus().value(),
                        null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.onFailure(
                        ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(),
                        ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus().value(),
                        null));
    }
}