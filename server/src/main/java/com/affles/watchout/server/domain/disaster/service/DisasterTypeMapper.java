package com.affles.watchout.server.domain.disaster.service;

import java.util.HashMap;
import java.util.Map;

public class DisasterTypeMapper {
    private static final Map<String, String> typeToKorean = new HashMap<>();

    static {
        typeToKorean.put("EQ", "지진");
        typeToKorean.put("WF", "산불");
        typeToKorean.put("FD", "홍수");
        typeToKorean.put("TC", "태풍");
    }

    public static String toKorean(String eventType) {
        return typeToKorean.getOrDefault(eventType, "재난");
    }
}