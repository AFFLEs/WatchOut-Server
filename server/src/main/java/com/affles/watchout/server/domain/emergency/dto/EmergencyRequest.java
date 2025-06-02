package com.affles.watchout.server.domain.emergency.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyRequest {
    private String reason;
    private Double latitude;
    private Double longitude;
}