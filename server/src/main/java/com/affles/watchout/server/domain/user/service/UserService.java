package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignInRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignInResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    SignUpResponse signUp(SignUpRequest request);
    SignInResponse signIn(SignInRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request);
}