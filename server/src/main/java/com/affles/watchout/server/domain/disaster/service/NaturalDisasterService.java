package com.affles.watchout.server.domain.disaster.service;

import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterEventResponse;
import com.affles.watchout.server.domain.disaster.dto.SimpleNotification;

import java.util.List;

public interface NaturalDisasterService {

    NaturalDisasterEventResponse[] fetchLatestDisasters(double latitude, double longitude);

    SimpleNotification toSimpleNotification(NaturalDisasterEventResponse resp);

    List<SimpleNotification> toSimpleNotificationList(NaturalDisasterEventResponse[] events);
}