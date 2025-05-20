package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.user.converter.UserConverter;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.AlertSettingRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.ConsentSettingRequest;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.global.exception.UserException;
import com.affles.watchout.server.global.jwt.JwtUtil;
import com.affles.watchout.server.global.status.ErrorStatus;
import com.affles.watchout.server.global.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RedisUtil redisUtil;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException(ErrorStatus.USER_ALREADY_EXISTS);
        }

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new UserException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 엔티티 변환 및 저장
        User user = UserConverter.toUser(request, encodedPassword);
        userRepository.save(user);

        return UserConverter.toSignUpResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        // 이메일은 맞지만 비밀번호 틀림
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(ErrorStatus.WRONG_PASSWORD);
        }

        // JWT 토큰 생성
        String accessToken = jwtUtil.generateToken(user.getUserId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId(), user.getEmail());

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        // Redis에 저장
        redisUtil.saveRefreshToken(user.getUserId().toString(), refreshToken, 60 * 60 * 24 * 14); // 2주

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new UserException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        Long userId = jwtUtil.getUserId(token);
        redisUtil.deleteRefreshToken(userId.toString());

        Long expiration = jwtUtil.getExpiration(token);
        redisUtil.addTokenToBlacklist(token, expiration);
    }

    // 유저 동의 설정 관련
    private User getUser(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(request));
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public void updateConsentSettings(ConsentSettingRequest request, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setAgreeEmergencyDataShare(request.getAgreeEmergencyDataShare());
        user.setAllowLocationTracking(request.getAllowLocationTracking());
    }

    @Override
    public void updateEmergencyConsent(Boolean value, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setAgreeEmergencyDataShare(value);
    }

    @Override
    public void updateLocationConsent(Boolean value, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setAllowLocationTracking(value);
    }

    @Override
    public void updateAlertSettings(AlertSettingRequest request, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setVibrationAlert(request.getVibrationAlert());
        user.setEnableWatchEmergencySignal(request.getEnableWatchEmergencySignal());
        user.setGuardianPhone(request.getGuardianPhone());
    }

    @Override
    public void updateVibrationAlert(Boolean value, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setVibrationAlert(value);
    }

    @Override
    public void updateWatchEmergency(Boolean value, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setEnableWatchEmergencySignal(value);
    }

    @Override
    public void updateGuardianPhone(String phone, HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);
        user.setGuardianPhone(phone);
    }
}