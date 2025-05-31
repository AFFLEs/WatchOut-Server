// sse 설정 코드 - 현재 사용 X
package com.affles.watchout.server.domain.disaster.controller;

import com.affles.watchout.server.domain.disaster.dto.SimpleNotification;
import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterEventResponse;
import com.affles.watchout.server.domain.disaster.service.NaturalDisasterService;
import com.affles.watchout.server.global.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class NaturalDisasterSseController {

    private final NaturalDisasterService naturalDisasterService;

    @Autowired
    public NaturalDisasterSseController(NaturalDisasterService naturalDisasterService) {
        this.naturalDisasterService = naturalDisasterService;
    }

    @GetMapping(path = "/sse/disasters", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamDisasters(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng) {

        SseEmitter emitter = new SseEmitter(0L);

        try {
            // 1) Ambee API 호출 (서비스 레이어에서 예외 발생 가능)
            NaturalDisasterEventResponse[] events =
                    naturalDisasterService.fetchLatestDisasters(lat, lng);

            // 2) SimpleNotification 리스트로 변환 (최대 1개)
            List<SimpleNotification> allNotifications =
                    naturalDisasterService.toSimpleNotificationList(events);

            // 3) 첫 번째 알림만 꺼내서 전달
            List<SimpleNotification> singleNotificationList =
                    Collections.singletonList(allNotifications.get(0));

            // 4) ApiResponse 성공 형태로 감싸서 전송
            ApiResponse<List<SimpleNotification>> response =
                    ApiResponse.onSuccess(singleNotificationList);
            emitter.send(response);

        } catch (IOException e) {
            // 5) SseEmitter.send() 중 IOException 발생 시 스트림 종료
            emitter.completeWithError(e);
            return emitter;
        }
        // 6) 정상적으로 한 번 전송 후 스트림 종료
        emitter.complete();
        return emitter;
    }
}