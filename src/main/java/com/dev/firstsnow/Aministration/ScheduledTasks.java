package com.dev.firstsnow.Aministration;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
//쿠키 쓰지 말고
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RestTemplate restTemplate;
    private final LocationMap locationMap;

    // 한 시간마다 실행되는 메서드
    @Scheduled(fixedRate = 3600000)
    public void doSomething() {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        // GET 요청 실행 및 결과 처리
        String response = restTemplate.getForObject(url, String.class);
        // 결과에 대한 추가적인 처리를 여기서 할 수 있습니다.
        System.out.println(response);
    }
}
