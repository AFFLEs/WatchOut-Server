package com.affles.watchout.server.domain.user.service;

import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.domain.travel.repository.TravelRepository;
import com.affles.watchout.server.domain.user.converter.UserConverter;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.*;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserSettingRequest.*;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.global.exception.UserException;
import com.affles.watchout.server.global.jwt.JwtUtil;
import com.affles.watchout.server.global.status.ErrorStatus;
import com.affles.watchout.server.global.redis.RedisUtil;
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
    private final TravelRepository travelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getUserInfo().getEmail()).isPresent()) {
            throw new UserException(ErrorStatus.USER_ALREADY_EXISTS);
        }

        // 비밀번호 확인
        if (!request.getUserInfo().getPassword().equals(request.getUserInfo().getConfirmPassword())) {
            throw new UserException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getUserInfo().getPassword());

        // 엔티티 변환 및 저장
        User user = UserConverter.toUser(request, encodedPassword);
        userRepository.save(user);

        travelRepository.save(Travel.builder()
                .user(user)
                .departDate(request.getTravelInfo().getDepartDate())
                .arriveDate(request.getTravelInfo().getArriveDate())
                .build());

        return UserConverter.toSignUpResponse(user, request);
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

        // 응답 헤더에 accessToken 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        // refreshToken → Redis 저장
        redisUtil.saveRefreshToken(user.getUserId().toString(), refreshToken, 60 * 60 * 24 * 14); // 2주로 설정

        // refreshToken → HttpOnly 쿠키로 전송
        jwtUtil.setCookie(response, "refreshToken", refreshToken, 60 * 60 * 24 * 14);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveAccessToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new UserException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        Long userId = jwtUtil.getUserId(token);
        redisUtil.deleteRefreshToken(userId.toString());

        Long expiration = jwtUtil.getExpiration(token);
        redisUtil.addTokenToBlacklist(token, expiration);

        jwtUtil.setCookie(response, "refreshToken", "", 0);
    }

    @Override
    public UserProfileResponse getUserProfile(HttpServletRequest requestHeader) {
        User user = getUser(requestHeader);

        return UserProfileResponse.builder()
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .phoneNumber(user.getPhoneNumber())
                .guardianPhone(user.getGuardianPhone())
                .vibrationAlert(user.getVibrationAlert())
                .enableWatchEmergencySignal(user.getEnableWatchEmergencySignal())
                .build();
    }

    @Override
    public LoginResponse refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 refreshToken 추출
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            throw new UserException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        // userId, email 추출
        Long userId = jwtUtil.getUserId(refreshToken);
        String email = jwtUtil.getEmail(refreshToken);

        // Redis에서 토큰 일치 확인
        String savedToken = redisUtil.getRefreshToken(userId.toString());
        if (!refreshToken.equals(savedToken)) {
            throw new UserException(ErrorStatus.REFRESH_TOKEN_INVALID);
        }

        // 새 accessToken 생성
        String newAccessToken = jwtUtil.generateToken(userId, email);

        // Authorization 헤더에 포함
        response.setHeader("Authorization", "Bearer " + newAccessToken);

        // 유저 정보 조회 (name 반환을 위해)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    // 유저 동의 설정 관련
    private User getUser(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveAccessToken(request));
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public EmergencyConsentResponse updateEmergencyConsent(Boolean value, HttpServletRequest requestHeader) {
        if (value == null) throw new UserException(ErrorStatus.EMERGENCY_CONSENT_REQUIRED);
        User user = getUser(requestHeader);
        user.setAgreeEmergencyDataShare(value);
        return EmergencyConsentResponse.builder()
                .agreeEmergencyDataShare(user.getAgreeEmergencyDataShare())
                .build();
    }

    @Override
    public LocationConsentResponse updateLocationConsent(Boolean value, HttpServletRequest requestHeader) {
        if (value == null) throw new UserException(ErrorStatus.LOCATION_CONSENT_REQUIRED);
        User user = getUser(requestHeader);
        user.setAllowLocationTracking(value);
        return LocationConsentResponse.builder()
                .allowLocationTracking(user.getAllowLocationTracking())
                .build();
    }

    @Override
    public VibrationResponse updateVibrationAlert(Boolean value, HttpServletRequest requestHeader) {
        if (value == null) throw new UserException(ErrorStatus.VIBRATION_REQUIRED);
        User user = getUser(requestHeader);
        user.setVibrationAlert(value);
        return VibrationResponse.builder()
                .vibrationAlert(user.getVibrationAlert())
                .build();
    }

    @Override
    public WatchEmergencyResponse updateWatchEmergency(Boolean value, HttpServletRequest requestHeader) {
        if (value == null) throw new UserException(ErrorStatus.WATCH_EMERGENCY_REQUIRED);
        User user = getUser(requestHeader);
        user.setEnableWatchEmergencySignal(value);
        return WatchEmergencyResponse.builder()
                .enableWatchEmergencySignal(user.getEnableWatchEmergencySignal())
                .build();
    }

    @Override
    public GuardianPhoneResponse updateGuardianPhone(String phone, HttpServletRequest requestHeader) {
        if (phone == null || phone.isBlank()) {
            throw new UserException(ErrorStatus.GUARDIAN_PHONE_REQUIRED);
        }
        User user = getUser(requestHeader);
        user.setGuardianPhone(phone);
        return GuardianPhoneResponse.builder()
                .guardianPhone(user.getGuardianPhone())
                .build();
    }
}