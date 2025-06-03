package com.affles.watchout.server.domain.emergency.service;

import com.affles.watchout.server.domain.emergency.dto.EmergencyRequest;
import com.affles.watchout.server.domain.emergency.dto.EmergencyResponse;
import com.affles.watchout.server.domain.emergency.entity.Emergency;
import com.affles.watchout.server.domain.emergency.repository.EmergencyRepository;
import com.affles.watchout.server.domain.spot.entity.Spot;
import com.affles.watchout.server.domain.spot.repository.SpotRepository;
import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.domain.travel.repository.TravelRepository;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.global.exception.TravelException;
import com.affles.watchout.server.global.exception.UserException;
import com.affles.watchout.server.global.exception.EmergencyException;
import com.affles.watchout.server.global.jwt.JwtUtil;
import com.affles.watchout.server.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmergencyServiceImpl implements EmergencyService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final EmergencyRepository emergencyRepository;
    private final SpotRepository spotRepository;
    private final TravelRepository travelRepository;
    private final SmsService smsService; // 보호자에게 문자 보내는 서비스

    @Override
    public EmergencyResponse handleEmergency(EmergencyRequest request, HttpServletRequest httpRequest) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveAccessToken(httpRequest));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        // Emergency 저장
        emergencyRepository.save(Emergency.builder()
                .user(user)
                .occurDate(LocalDateTime.now())
                .reason(request.getReason())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build());

        // Travel → Spot 조회 - 응급 상황 발생일 기준
        Travel travel = travelRepository.findFirstByUser(user)
                .orElseThrow(() -> new TravelException(ErrorStatus.TRAVEL_NOT_FOUND));

        List<Spot> todaySpots = spotRepository.findAllByTravelAndSpotDateOrderBySpotTimeAsc(travel, LocalDate.now());

        // Spot 문자열 목록 만들기
        List<String> spotList = todaySpots.stream()
                .map(s -> String.format("%s %s / %s",
                        s.getSpotTime(), s.getSpotName(), s.getSpotDetail()))
                .toList();

        // 보호자 메시지 전송
        String message = String.format("""
                [WatchOut 응급 상황 발생 안내]
                %s님 보호자 연락처로 등록되어 있어 연락드립니다.
        
                현재, %s님 응급 상황 발생으로 판단됩니다.
        
                원인: %s
                위치: 위도 %.6f, 경도 %.6f
                최근 경로:
                %s
        
                연락을 취해 확인해보세요.
                """,
                user.getName(), user.getName(),
                request.getReason(),
                request.getLatitude(), request.getLongitude(),
                String.join("\n", spotList)
        );

        try {
            smsService.send(user.getGuardianPhone(), message);
        } catch (Exception e) {
            throw new EmergencyException(ErrorStatus.EMERGENCY_SEND_SMS_FAILED);
        }

        return EmergencyResponse.builder()
                .userName(user.getName())
                .reason(request.getReason())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .spot(spotList)
                .build();
    }
}