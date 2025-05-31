package com.affles.watchout.server.domain.spot.service;

import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.SpotInfo;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotRequest.CreateSpotRequest;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.DeletedSpotInfo;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface SpotService {
    SpotInfo createSpot(CreateSpotRequest request, HttpServletRequest requestHeader);
    Map<LocalDate, List<SpotInfo>> getLatestSpotsByAllDates(HttpServletRequest requestHeader);
    List<SpotInfo> getSpotDetailsByDate(LocalDate date, HttpServletRequest requestHeader);
    DeletedSpotInfo deleteSpot(Long spotId, HttpServletRequest requestHeader);
}