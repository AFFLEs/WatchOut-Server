package com.affles.watchout.server.domain.disaster.service;

import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterApiResponse;
import com.affles.watchout.server.domain.disaster.dto.NaturalDisasterEventResponse;
import com.affles.watchout.server.domain.disaster.dto.SimpleNotification;
import com.affles.watchout.server.global.exception.DisasterException;
import com.affles.watchout.server.global.status.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NaturalDisasterServiceImpl implements NaturalDisasterService {

    private final RestTemplate restTemplate;

    @Value("${ambee.naturaldisasters.base-url}")
    private String ambeeBaseUrl;

    @Value("${ambee.api.key}")
    private String ambeeApiKey;

    public NaturalDisasterServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public NaturalDisasterEventResponse[] fetchLatestDisasters(double latitude, double longitude) {
        // 1) 파라미터 범위 검증
        if (latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0) {
            throw new DisasterException(ErrorStatus.DISASTER_PARAM_INVALID);
        }

        // 2) Ambee API URL 조합 (최신 1개만 가져옴)
        String url = String.format(
                "%s/disasters/latest/by-lat-lng?lat=%f&lng=%f&limit=1&page=1",
                ambeeBaseUrl, latitude, longitude
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", ambeeApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        NaturalDisasterApiResponse apiResp;
        try {
            // 3) REST 호출 (RestClientException 발생 가능)
            ResponseEntity<NaturalDisasterApiResponse> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, entity, NaturalDisasterApiResponse.class);
            apiResp = responseEntity.getBody();
        } catch (RestClientException rcEx) {
            // 외부 API 호출 실패 시
            throw new DisasterException(
                    ErrorStatus.DISASTER_EXTERNAL_API_ERROR,
                    "Ambee API 호출 실패: " + rcEx.getMessage()
            );
        }

        // 4) 결과가 null이거나 비어 있으면 Not Found 예외
        if (apiResp == null || apiResp.getResult() == null || apiResp.getResult().length == 0) {
            throw new DisasterException(ErrorStatus.DISASTER_NOT_FOUND);
        }

        // 5) 정상일 경우, 이벤트 배열 리턴
        return apiResp.getResult();
    }

    @Override
    public SimpleNotification toSimpleNotification(NaturalDisasterEventResponse resp) {
        if (resp == null) {
            return null;
        }

        // eventType 코드 → 한국어 재난명
        String koreanType = DisasterTypeMapper.toKorean(resp.getEventType());

        // title 예: "실시간 (지진) 안내"
        String title = String.format("실시간 (%s) 안내", koreanType);

        // contents 예: "현재 지역에 지진이 발생했습니다"
        String contents = String.format("현재 지역에 %s이 발생했습니다", koreanType);

        return new SimpleNotification(title, contents);
    }

    @Override
    public List<SimpleNotification> toSimpleNotificationList(NaturalDisasterEventResponse[] events) {
        if (events == null || events.length == 0) {
            return Collections.emptyList();
        }
        List<SimpleNotification> list = new ArrayList<>();
        for (NaturalDisasterEventResponse ev : events) {
            SimpleNotification sn = toSimpleNotification(ev);
            if (sn != null) {
                list.add(sn);
            }
        }
        return list;
    }
}
