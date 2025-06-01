package com.affles.watchout.server.domain.spot.converter;

import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.SpotInfo;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotRequest.CreateSpotRequest;
import com.affles.watchout.server.domain.spot.entity.Spot;
import com.affles.watchout.server.domain.travel.entity.Travel;

public class SpotConverter {

    public static Spot toSpot(CreateSpotRequest req, Travel travel) {
        return Spot.builder()
                .travel(travel)
                .spotDate(req.getSpotDate())
                .spotTime(req.getSpotTime())
                .spotName(req.getSpotName())
                .spotDetail(req.getSpotDetail())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .city(req.getCity())
                .country(req.getCountry())
                .isPlan(req.getIsPlan())
                .build();
    }

    public static SpotInfo toSpotInfo(Spot spot) {
        return SpotInfo.builder()
                .spotId(spot.getSpotId())
                .spotDate(spot.getSpotDate())
                .spotTime(spot.getSpotTime())
                .spotName(spot.getSpotName())
                .spotDetail(spot.getSpotDetail())
                .latitude(spot.getLatitude())
                .longitude(spot.getLongitude())
                .city(spot.getCity())
                .country(spot.getCountry())
                .isPlan(spot.getIsPlan())
                .build();
    }
}

