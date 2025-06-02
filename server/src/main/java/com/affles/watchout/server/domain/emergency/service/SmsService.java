package com.affles.watchout.server.domain.emergency.service;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import net.nurigo.sdk.NurigoApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    private final DefaultMessageService messageService;
    private final String fromNumber;

    public SmsService(
            @Value("${solapi.apiKey}") String apiKey,
            @Value("${solapi.apiSecret}") String apiSecret,
            @Value("${solapi.from}") String fromNumber,
            @Value("${solapi.base-url}") String baseUrl
    ) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, baseUrl);
        this.fromNumber = fromNumber;
    }

    public void send(String to, String text) {
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(to);
        message.setText(text);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("✅ 문자 전송 성공 - 수신자: {}, 응답: {}", to, response);
        } catch (Exception e) {
            log.error("🚫 문자 전송 실패 - 수신자: {}, 메시지: {}", to, e.getMessage(), e);
        }
    }
}