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
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 없습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    // 사용자 동의/허용 관련 에러
    GUARDIAN_PHONE_REQUIRED(HttpStatus.BAD_REQUEST, "보호자 연락처는 필수입니다."),
    CONSENT_FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "응급 데이터 공유 허용 및 위치 추적 허용 필드는 모두 필수입니다."),
    EMERGENCY_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "응급 상황 데이터 공유 동의 여부는 필수입니다."),
    LOCATION_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "위치 추적 허용 여부는 필수입니다."),
    ALERT_FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "진동, 구조 요청, 보호자 번호는 모두 필수입니다."),
    VIBRATION_REQUIRED(HttpStatus.BAD_REQUEST, "진동 알림 여부는 필수입니다."),
    WATCH_EMERGENCY_REQUIRED(HttpStatus.BAD_REQUEST, "긴급 구조 요청 여부는 필수입니다."),

    // 여행 관련 에러
    TRAVEL_NOT_FOUND(HttpStatus.NOT_FOUND, "등록한 여행 일정이 없습니다."),
    TRAVEL_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "출발일과 도착일은 필수입니다."),

    // 장소 관련 에러
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장소를 찾을 수 없습니다."),
    SPOT_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "장소 날짜는 필수입니다."),
    SPOT_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 장소에 접근할 수 없습니다."),
    SPOT_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "장소명이 필요합니다."),
    SPOT_DETAIL_REQUIRED(HttpStatus.BAD_REQUEST, "장소 상세 주소가 필요합니다."),
    SPOT_PLAN_REQUIRED(HttpStatus.BAD_REQUEST, "장소 수동/자동 여부는 필수입니다."),

    // 자연재해 관련 에러
    DISASTER_PARAM_INVALID(HttpStatus.BAD_REQUEST, "잘못된 위도/경도 입니다."),
    DISASTER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 위치에 재난 이벤트가 존재하지 않습니다."),
    DISASTER_EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 재난 정보 조회에 실패했습니다."),

    // 응급 상황 관련
    EMERGENCY_SEND_SMS_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "응급 문자 전송에 실패했습니다."),
    EMERGENCY_NOT_ALLOWED(HttpStatus.FORBIDDEN, "응급 기능 사용 권한이 없습니다.");

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