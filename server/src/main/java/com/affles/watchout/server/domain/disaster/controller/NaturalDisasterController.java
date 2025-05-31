package com.affles.watchout.server.domain.disaster.controller;

import com.affles.watchout.server.domain.disaster.dto.SimpleNotification;
import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterEventResponse;
import com.affles.watchout.server.domain.disaster.service.NaturalDisasterService;
import com.affles.watchout.server.global.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
            @RequestParam("lng") double lng) {

        // 1) 서비스 호출 → 예외 발생 시 GlobalExceptionHandler에게 위임
        NaturalDisasterEventResponse[] events = naturalDisasterService.fetchLatestDisasters(lat, lng);

        // 2) SimpleNotification 리스트로 변환
        List<SimpleNotification> notifications = naturalDisasterService.toSimpleNotificationList(events);

        // 3) 최신 1건만 꺼내서 반환 (서비스 레이어에서 데이터 없을 경우 이미 예외가 던져짐)
        List<SimpleNotification> single = Collections.singletonList(notifications.get(0));
        ApiResponse<List<SimpleNotification>> response = ApiResponse.onSuccess(single);

        return ResponseEntity.ok(response);
    }
}