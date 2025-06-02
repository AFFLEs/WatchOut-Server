package com.affles.watchout.server.domain.emergency.service;

import com.affles.watchout.server.domain.emergency.dto.EmergencyRequest;
import com.affles.watchout.server.domain.emergency.dto.EmergencyResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface EmergencyService {
    EmergencyResponse handleEmergency(EmergencyRequest request, HttpServletRequest requestHeader);
}
