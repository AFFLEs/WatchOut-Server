package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.LoginRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.LoginResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    SignUpResponse signUp(SignUpRequest request);
    LoginResponse login(LoginRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request);
}