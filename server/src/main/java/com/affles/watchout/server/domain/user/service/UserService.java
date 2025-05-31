package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    SignUpResponse signUp(SignUpRequest request);
    LoginResponse login(LoginRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request);

    UserProfileResponse getUserProfile(HttpServletRequest requestHeader);

    // 유저 동의 설정 관련
    EmergencyConsentResponse updateEmergencyConsent(Boolean value, HttpServletRequest requestHeader);
    LocationConsentResponse updateLocationConsent(Boolean value, HttpServletRequest requestHeader);
    VibrationResponse updateVibrationAlert(Boolean value, HttpServletRequest requestHeader);
    WatchEmergencyResponse updateWatchEmergency(Boolean value, HttpServletRequest requestHeader);
    GuardianPhoneResponse updateGuardianPhone(String phone, HttpServletRequest requestHeader);
}