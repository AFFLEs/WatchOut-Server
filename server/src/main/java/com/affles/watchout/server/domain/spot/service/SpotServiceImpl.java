package com.affles.watchout.server.domain.spot.service;

import com.affles.watchout.server.domain.spot.converter.SpotConverter;
import com.affles.watchout.server.domain.spot.entity.Spot;
import com.affles.watchout.server.domain.spot.repository.SpotRepository;
import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.domain.travel.repository.TravelRepository;
import com.affles.watchout.server.domain.user.entity.User;
import com.affles.watchout.server.domain.user.repository.UserRepository;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.SpotInfo;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotRequest.CreateSpotRequest;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.DeletedSpotInfo;
import com.affles.watchout.server.global.exception.TravelException;
import com.affles.watchout.server.global.exception.UserException;
import com.affles.watchout.server.global.exception.SpotException;
import com.affles.watchout.server.global.jwt.JwtUtil;
import com.affles.watchout.server.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SpotServiceImpl implements SpotService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final SpotRepository spotRepository;

    @Override
    public SpotInfo createSpot(CreateSpotRequest request, HttpServletRequest requestHeader) {
        if (request.getSpotDate() == null) throw new SpotException(ErrorStatus.SPOT_DATE_REQUIRED);
        if (request.getSpotName() == null || request.getSpotName().isBlank()) throw new SpotException(ErrorStatus.SPOT_NAME_REQUIRED);
        if (request.getSpotDetail() == null || request.getSpotDetail().isBlank()) throw new SpotException(ErrorStatus.SPOT_DETAIL_REQUIRED);
        if (request.getIsPlan() == null) throw new SpotException(ErrorStatus.SPOT_PLAN_REQUIRED);

        Travel travel = getTravelFromRequest(requestHeader);
        Spot spot = SpotConverter.toSpot(request, travel);
        spotRepository.save(spot);
        return SpotConverter.toSpotInfo(spot);
    }

    @Override
    public Map<LocalDate, List<SpotInfo>> getLatestSpotsByAllDates(HttpServletRequest requestHeader) {
        Travel travel = getTravelFromRequest(requestHeader);
        List<Spot> allSpots = spotRepository.findAllByTravel(travel);

        return allSpots.stream()
                .collect(Collectors.groupingBy(
                        Spot::getSpotDate,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Spot::getSpotTime, Comparator.nullsLast(Comparator.reverseOrder())))
                                        .limit(3)
                                        .map(SpotConverter::toSpotInfo)
                                        .toList()
                        )
                ));
    }
    @Override
    public List<SpotInfo> getSpotDetailsByDate(LocalDate date, HttpServletRequest requestHeader) {
        Travel travel = getTravelFromRequest(requestHeader);
        return spotRepository.findAllByTravelAndSpotDateOrderBySpotTimeAsc(travel, date)
                .stream()
                .map(SpotConverter::toSpotInfo)
                .toList();
    }

    @Override
    public DeletedSpotInfo deleteSpot(Long spotId, HttpServletRequest requestHeader) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveAccessToken(requestHeader));
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotException(ErrorStatus.SPOT_NOT_FOUND));

        if (!spot.getTravel().getUser().getUserId().equals(userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        spotRepository.delete(spot);

        return DeletedSpotInfo.builder()
                .spotId(spotId)
                .spotName(spot.getSpotName())
                .build();
    }

    private Travel getTravelFromRequest(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(jwtUtil.resolveAccessToken(request));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        return travelRepository.findFirstByUser(user)
                .orElseThrow(() -> new TravelException(ErrorStatus.TRAVEL_NOT_FOUND));
    }
}