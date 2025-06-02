package com.affles.watchout.server.domain.emergency.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void send(String phoneNumber, String message) {
        // 실제로 보내려면 외부 문자 API 연동해야 함
        System.out.println("보내는 번호: " + phoneNumber);
        System.out.println("메시지 내용:\n" + message);
    }
}