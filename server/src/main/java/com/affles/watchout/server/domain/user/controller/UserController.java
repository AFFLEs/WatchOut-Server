package com.affles.watchout.server.domain.user.controller;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.*;
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

    // accessToken 재발급
    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return ApiResponse.onSuccess(userService.refreshAccessToken(request, response));
    }

    @GetMapping("/info")
    public ApiResponse<UserProfileResponse> getUserProfile(HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.getUserProfile(httpRequest));
    }

    // 유저 동의 설정 관련
    // (1) 응급 상황 데이터 동의 설정
    @PatchMapping("/settings/emergency")
    public ApiResponse<EmergencyConsentResponse> updateEmergencyConsent(@RequestBody EmergencyConsentRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.updateEmergencyConsent(request.getAgreeEmergencyDataShare(), httpRequest));
    }

    // (2) 위치 추적 허용 설정
    @PatchMapping("/settings/location")
    public ApiResponse<LocationConsentResponse> updateLocationConsent(@RequestBody LocationConsentRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.updateLocationConsent(request.getAllowLocationTracking(), httpRequest));
    }

    // (3) 진동 설정
    @PatchMapping("/settings/vibration")
    public ApiResponse<VibrationResponse> updateVibrationAlert(@RequestBody VibrationRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.updateVibrationAlert(request.getVibrationAlert(), httpRequest));
    }

    // (4) 구조 요청 설정
    @PatchMapping("/settings/watch-emergency")
    public ApiResponse<WatchEmergencyResponse> updateWatchEmergency(@RequestBody WatchEmergencyRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.updateWatchEmergency(request.getEnableWatchEmergencySignal(), httpRequest));
    }

    // (5) 보호자 번호 설정
    @PatchMapping("/settings/guardian-phone")
    public ApiResponse<GuardianPhoneResponse> updateGuardianPhone(@RequestBody GuardianPhoneRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(userService.updateGuardianPhone(request.getGuardianPhone(), httpRequest));
    }

}