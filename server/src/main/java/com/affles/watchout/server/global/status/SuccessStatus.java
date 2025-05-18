package com.affles.watchout.server.global.status;

import com.affles.watchout.server.global.BaseCode;
import com.affles.watchout.server.global.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "요청에 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .isSuccess(true)
                .code(httpStatus.value())
                .message(message)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .isSuccess(true)
                .code(httpStatus.value())
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}