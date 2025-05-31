// sse용 DTO라 현재 사용 X
package com.affles.watchout.server.domain.disaster.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisasterAlertMessage {

    private String type;                    // ex) "EQ", "WF", "FD", "TC" 등 (event_type)
    private String eventName;               // ex) "Earthquake", "Fire incident" 등 (event_name)
    private String date;                    // 이벤트 발생 일시 (String 형태)

    private double latitude;                // 위도
    private double longitude;               // 경도

    private String proximitySeverityLevel;  // 근접 심각도 레벨 (예: "Low Risk")
    private String defaultAlertLevels;      // 기본 알림 레벨 (예: "Orange")
    private String estimatedEndDate;        // 예상 종료 시각

    // 지진 전용 정보 (지진이 아닐 땐 0.0, null)
    private double magnitude;               // ex) 4.2 (지진 전용)
    private double alertScore;              // ex) 0.85 (지진 전용)
    private String severity;                // ex) "moderate" (지진 전용)

    private LocalDateTime timestamp;        // 서버가 메시지를 생성한 시각
}