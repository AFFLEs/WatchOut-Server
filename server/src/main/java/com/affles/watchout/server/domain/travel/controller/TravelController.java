package com.affles.watchout.server.domain.travel.controller;

import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelRequest.CreateTravelRequest;
import com.affles.watchout.server.domain.travel.dto.TravelDTO.TravelResponse.TravelInfoResponse;
import com.affles.watchout.server.domain.travel.service.TravelService;
import com.affles.watchout.server.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ApiResponse<TravelInfoResponse> createTravel(@RequestBody CreateTravelRequest request, HttpServletRequest httpRequest) {
        TravelInfoResponse response = travelService.createTravel(request, httpRequest);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping
    public ApiResponse<TravelInfoResponse> getTravel(HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(travelService.getTravel(httpRequest));
    }
}