package com.affles.watchout.server.domain.user.controller;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignInRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignInResponse;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;
import com.affles.watchout.server.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request) {
        SignUpResponse response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest request, HttpServletResponse response) {
        SignInResponse result = userService.signIn(request, response);
        return ResponseEntity.ok(result);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok().build();
    }

}