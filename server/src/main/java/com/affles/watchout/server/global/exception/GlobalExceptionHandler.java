package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.common.ApiResponse;
import com.affles.watchout.server.global.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(GeneralException e) {
        ErrorStatus status = e.getErrorStatus();
        log.warn("[GeneralException] {}", status.getMessage());

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(
                        status.getMessage(),
                        status.getHttpStatus().value(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleOtherException(Exception e) {
        log.error("[UnhandledException] ", e);
        ErrorStatus status = ErrorStatus._INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(
                        status.getMessage(),
                        status.getHttpStatus().value(),
                        null
                ));
    }
}