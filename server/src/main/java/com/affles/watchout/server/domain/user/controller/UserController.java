package com.affles.watchout.server.domain.user.controller;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.LoginRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.LoginResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.AlertSettingRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.ConsentSettingRequest;
import com.affles.watchout.server.domain.user.service.UserService;
import com.affles.watchout.server.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.onSuccess(userService.signUp(request));
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return ApiResponse.onSuccess(userService.login(request, response));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        userService.logout(request);
        return ApiResponse.onSuccess(null);
    }

    // 유저 동의 설정 관련
    // (0) 응급 데이터 + 위치 추적 동시 설정
    @PatchMapping("/consents")
    public ApiResponse<Void> updateConsents(@RequestBody ConsentSettingRequest request, HttpServletRequest httpRequest) {
        userService.updateConsentSettings(request, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (1) 응급 상황 데이터 동의 설정
    @PatchMapping("/settings/emergency")
    public ApiResponse<Void> updateEmergencyConsent(@RequestParam Boolean value, HttpServletRequest httpRequest) {
        userService.updateEmergencyConsent(value, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (2) 위치 추적 허용 설정
    @PatchMapping("/settings/location")
    public ApiResponse<Void> updateLocationConsent(@RequestParam Boolean value, HttpServletRequest httpRequest) {
        userService.updateLocationConsent(value, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (3) 진동 + 구조요청 + 보호자번호 동시 설정
    @PatchMapping("/alerts")
    public ApiResponse<Void> updateAlertSettings(@RequestBody AlertSettingRequest request, HttpServletRequest httpRequest) {
        userService.updateAlertSettings(request, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (4) 진동 설정
    @PatchMapping("/settings/vibration")
    public ApiResponse<Void> updateVibrationAlert(@RequestParam Boolean value, HttpServletRequest httpRequest) {
        userService.updateVibrationAlert(value, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (5) 구조 요청 설정
    @PatchMapping("/settings/watch-emergency")
    public ApiResponse<Void> updateWatchEmergency(@RequestParam Boolean value, HttpServletRequest httpRequest) {
        userService.updateWatchEmergency(value, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    // (6) 보호자 번호 설정
    @PatchMapping("/settings/guardian-phone")
    public ApiResponse<Void> updateGuardianPhone(@RequestParam String phone, HttpServletRequest httpRequest) {
        userService.updateGuardianPhone(phone, httpRequest);
        return ApiResponse.onSuccess(null);
    }

}