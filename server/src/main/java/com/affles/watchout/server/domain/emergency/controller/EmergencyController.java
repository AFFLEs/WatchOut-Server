package com.affles.watchout.server.domain.emergency.controller;

import com.affles.watchout.server.domain.emergency.dto.EmergencyRequest;
import com.affles.watchout.server.domain.emergency.dto.EmergencyResponse;
import com.affles.watchout.server.domain.emergency.service.EmergencyService;
import com.affles.watchout.server.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
public class EmergencyController {

    private final EmergencyService emergencyService;

    @PostMapping
    public ApiResponse<EmergencyResponse> reportEmergency(
            @RequestBody EmergencyRequest request,
            HttpServletRequest httpRequest) {

        EmergencyResponse response = emergencyService.handleEmergency(request, httpRequest);
        return ApiResponse.onSuccess(response);
    }

}