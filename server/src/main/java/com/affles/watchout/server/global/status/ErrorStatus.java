package com.affles.watchout.server.global.status;

import com.affles.watchout.server.global.BaseErrorCode;
import com.affles.watchout.server.global.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 공통 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 금지되었습니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // 사용자 관련 에러
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않거나 존재하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    // 여행 관련 에러
    TRAVEL_NOT_FOUND(HttpStatus.NOT_FOUND, "등록한 여행 일정이 없습니다."),
    TRAVEL_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "출발일과 도착일은 필수입니다."),

    // 장소 관련 에러
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장소를 찾을 수 없습니다."),
    SPOT_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "장소 날짜는 필수입니다."),
    SPOT_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 장소에 접근할 수 없습니다."),
    SPOT_TIME_REQUIRED(HttpStatus.BAD_REQUEST, "방문 시간이 필요합니다."),
    SPOT_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "장소명이 필요합니다."),
    SPOT_DETAIL_REQUIRED(HttpStatus.BAD_REQUEST, "장소 상세 주소가 필요합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(httpStatus.value())
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(httpStatus.value())
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}