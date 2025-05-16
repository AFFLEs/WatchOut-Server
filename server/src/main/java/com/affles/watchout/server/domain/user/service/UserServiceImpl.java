package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.converter.UserConverter;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.*;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 엔티티 변환 및 저장
        User user = UserConverter.toUser(request, encodedPassword);
        userRepository.save(user);

        return UserConverter.toSignUpResponse(user);
    }

    @Override
    public SignInResponse signIn(SignInRequest request, HttpServletResponse response) {
        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtUtil.generateToken(user.getUserId(), user.getEmail());

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken("dummy-refresh-token") // 추후 구현 예정!!
                .build();
    }
}