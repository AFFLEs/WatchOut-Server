package com.affles.watchout.server.domain.travel.converter;

import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelResponse.TravelInfoResponse;
import com.affles.watchout.server.domain.travel.entity.Travel;

public class TravelConverter {

    public static TravelInfoResponse toTravelInfoResponse(Travel travel) {
        return TravelInfoResponse.builder()
                .travelId(travel.getTravelId())
                .departDate(travel.getDepartDate())
                .arriveDate(travel.getArriveDate())
                .build();
    }
}