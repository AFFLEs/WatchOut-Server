package com.affles.watchout.server.domain.disaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaturalDisasterEventResponse {

    // 이벤트 타입 ("EQ"=지진, "WF"=산불, "FD"=홍수, "TC"=태풍 등)
    @JsonProperty("event_type")
    private String eventType;

    // 이벤트 이름 (예: "Earthquake", "Fire incident" 등)
    @JsonProperty("event_name")
    private String eventName;

    // 이벤트 발생 날짜/시간 (예: "2025-05-23 00:00:00")
    @JsonProperty("date")
    private String date;

    // 위도
    @JsonProperty("lat")
    private Double latitude;

    // 경도
    @JsonProperty("lng")
    private Double longitude;

    // 재난 근접 심각도 레벨 (“Low Risk”, “Moderate Risk” 등)
    @JsonProperty("proximity_severity_level")
    private String proximitySeverityLevel;

    // 기본 알림 레벨 (“Green”, “Orange”, “Red” 등)
    @JsonProperty("default_alert_levels")
    private String defaultAlertLevels;

    // 이벤트 예상 종료일 (예: "2025-06-22T22:55:02.000Z")
    @JsonProperty("estimated_end_date")
    private String estimatedEndDate;

    // (지진 전용)
    @JsonProperty("magnitude")
    private Double magnitude;

    @JsonProperty("alert_score")
    private Double alertScore;

    @JsonProperty("severity")
    private String severity;
}