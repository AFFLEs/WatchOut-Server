package com.affles.watchout.server.domain.spot.controller;

import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotRequest.CreateSpotRequest;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.SpotInfo;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.DeletedSpotInfo;
import com.affles.watchout.server.domain.spot.service.SpotService;
import com.affles.watchout.server.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;
    @PostMapping
    public ApiResponse<SpotInfo> createSpot(@RequestBody CreateSpotRequest request, HttpServletRequest httpRequest) {
        SpotInfo createdSpot = spotService.createSpot(request, httpRequest);
        return ApiResponse.onSuccess(createdSpot);
    }

    @GetMapping("/latest")
    public ApiResponse<List<SpotInfo>> getLatestSpots(@RequestParam("date") LocalDate date, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(spotService.getLatestSpotsByDate(date, httpRequest));
    }

    @GetMapping("/detail")
    public ApiResponse<List<SpotInfo>> getSpotsByDate(@RequestParam("date") LocalDate date, HttpServletRequest httpRequest) {
        return ApiResponse.onSuccess(spotService.getSpotDetailsByDate(date, httpRequest));
    }

    @DeleteMapping("/{spotId}")
    public ApiResponse<DeletedSpotInfo> deleteSpot(@PathVariable Long spotId, HttpServletRequest httpRequest) {
        DeletedSpotInfo deletedInfo = spotService.deleteSpot(spotId, httpRequest);
        return ApiResponse.onSuccess(deletedInfo);
    }
}