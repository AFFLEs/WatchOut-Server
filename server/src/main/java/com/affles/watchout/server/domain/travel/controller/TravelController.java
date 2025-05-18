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
    public ApiResponse<Void> createTravel(@RequestBody CreateTravelRequest request, HttpServletRequest httpRequest) {
        travelService.createTravel(request, httpRequest);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping
    public ApiResponse<TravelInfoResponse> getTravel(HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(travelService.getTravel(httpRequest));
    }
}