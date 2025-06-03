package com.affles.watchout.server.domain.disaster.controller;

import com.affles.watchout.server.domain.disaster.dto.SimpleNotification;
import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterEventResponse;
import com.affles.watchout.server.domain.disaster.service.NaturalDisasterService;
import com.affles.watchout.server.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/disasters")
@Validated
public class NaturalDisasterController {

    private final NaturalDisasterService naturalDisasterService;

    @Autowired
    public NaturalDisasterController(NaturalDisasterService naturalDisasterService) {
        this.naturalDisasterService = naturalDisasterService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SimpleNotification>>> getLatestDisaster(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            HttpServletRequest request) {

        log.info("🔥 [Disaster API 호출됨]");
        log.info("🔥 [요청 URI] {}", request.getRequestURI());
        log.info("🔥 [Authorization 헤더] {}", request.getHeader("Authorization"));

        NaturalDisasterEventResponse[] events = naturalDisasterService.fetchLatestDisasters(lat, lng);
        List<SimpleNotification> notifications = naturalDisasterService.toSimpleNotificationList(events);
        List<SimpleNotification> single = Collections.singletonList(notifications.get(0));

        ApiResponse<List<SimpleNotification>> response = ApiResponse.onSuccess(single);
        return ResponseEntity.ok(response);
    }
}