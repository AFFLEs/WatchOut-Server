package com.affles.watchout.server.domain.travel.service;

import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelRequest.CreateTravelRequest;
import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelResponse.TravelInfoResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface TravelService {

    void createTravel(CreateTravelRequest request, HttpServletRequest httpServletRequest);

    TravelInfoResponse getTravel(HttpServletRequest httpServletRequest);
}