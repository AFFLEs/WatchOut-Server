package com.affles.watchout.server.domain.spot.service;

import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.SpotInfo;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotRequest.CreateSpotRequest;
import com.affles.watchout.server.domain.spot.dto.SpotDTO.SpotResponse.DeletedSpotInfo;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;


public interface SpotService {
    SpotInfo createSpot(CreateSpotRequest request, HttpServletRequest requestHeader);
    List<SpotInfo> getLatestSpotsByDate(LocalDate date, HttpServletRequest requestHeader);
    List<SpotInfo> getSpotDetailsByDate(LocalDate date, HttpServletRequest requestHeader);
    DeletedSpotInfo deleteSpot(Long spotId, HttpServletRequest requestHeader);
}