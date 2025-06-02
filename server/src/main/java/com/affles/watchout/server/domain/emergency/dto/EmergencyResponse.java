package com.affles.watchout.server.domain.emergency.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyResponse {
    private String userName;
    private String reason;
    private Double latitude;
    private Double longitude;
    private List<String> spot; // "08:30 경복궁 / 서울시 종로구 사직로 161" 형태
}