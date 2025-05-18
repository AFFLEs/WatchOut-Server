package com.affles.watchout.server.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.affles.watchout.server.global.status.SuccessStatus;
import com.affles.watchout.server.global.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"status", "message", "code", "data"})
public class ApiResponse<T> {

    private final String status; // success 혹은 fail
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 성공 응답
    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(
                "success",
                SuccessStatus._OK.getMessage(),
                SuccessStatus._OK.getHttpStatus().value(),
                data
        );
    }

    public static <T> ApiResponse<T> of(BaseCode status, T data) {
        return new ApiResponse<>(
                "success",
                status.getReasonHttpStatus().getMessage(),
                status.getReasonHttpStatus().getHttpStatus().value(),
                data
        );
    }

    // 실패 응답
    public static <T> ApiResponse<T> onFailure(String message, int code, T data) {
        return new ApiResponse<>("fail", message, code, data);
    }
}