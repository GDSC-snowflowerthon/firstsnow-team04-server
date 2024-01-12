package com.dev.firstsnow.Aministration;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RestTemplate restTemplate;

    // 한 시간마다 실행되는 메서드
    @Scheduled(fixedRate = 3600000)
    public void doSomething() {

    }
}
