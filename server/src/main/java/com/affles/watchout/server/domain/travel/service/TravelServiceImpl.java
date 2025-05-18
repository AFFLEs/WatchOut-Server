package com.affles.watchout.server.domain.travel.service;

import com.affles.watchout.server.domain.travel.converter.TravelConverter;
import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelRequest.CreateTravelRequest;
import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelResponse.TravelInfoResponse;
import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.domain.travel.repository.TravelRepository;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.global.exception.TravelException;
import com.affles.watchout.server.global.exception.UserException;
import com.affles.watchout.server.global.jwt.JwtUtil;
import com.affles.watchout.server.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public TravelInfoResponse createTravel(CreateTravelRequest request, HttpServletRequest httpServletRequest) {
        if (request.getDepartDate() == null || request.getArriveDate() == null) {
            throw new TravelException(ErrorStatus.TRAVEL_DATE_REQUIRED);
        }

        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(httpServletRequest));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        Travel travel = Travel.builder()
                .user(user)
                .departDate(request.getDepartDate())
                .arriveDate(request.getArriveDate())
                .build();

        travelRepository.save(travel);

        return TravelConverter.toTravelInfoResponse(travel);
    }

    @Override
    public TravelInfoResponse getTravel(HttpServletRequest httpServletRequest) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(httpServletRequest));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        Travel travel = travelRepository.findFirstByUser(user)
                .orElseThrow(() -> new TravelException(ErrorStatus.TRAVEL_NOT_FOUND));

        return TravelConverter.toTravelInfoResponse(travel);
    }
}
