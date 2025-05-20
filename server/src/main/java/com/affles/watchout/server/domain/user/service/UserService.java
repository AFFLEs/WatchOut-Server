package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.LoginRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.LoginResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.AlertSettingRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.ConsentSettingRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    SignUpResponse signUp(SignUpRequest request);
    LoginResponse login(LoginRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request);

    // 유저 동의 설정 관련
    void updateConsentSettings(ConsentSettingRequest request, HttpServletRequest requestHeader);
    void updateEmergencyConsent(Boolean value, HttpServletRequest requestHeader);
    void updateLocationConsent(Boolean value, HttpServletRequest requestHeader);
    void updateAlertSettings(AlertSettingRequest request, HttpServletRequest requestHeader);
    void updateVibrationAlert(Boolean value, HttpServletRequest requestHeader);
    void updateWatchEmergency(Boolean value, HttpServletRequest requestHeader);
    void updateGuardianPhone(String phone, HttpServletRequest requestHeader);
}