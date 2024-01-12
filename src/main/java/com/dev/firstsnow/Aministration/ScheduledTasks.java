package com.dev.firstsnow.Aministration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
//쿠키 쓰지 말고
@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final RestTemplate restTemplate;
    private final LocationMap locationMap;

    // 정각마다 실행되는 메서드
    @Scheduled(cron = "0 0 * * * ?")
    public void doSomething() {

    }

    public Boolean lookUpWeather(int nx, int ny) throws IOException, JSONException {
        // 현재 시간으로부터 10분 전
        LocalDateTime nowMinusTenMinutes = LocalDateTime.now().minusMinutes(10);

        // 날짜와 시간을 원하는 형식으로 포맷팅
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        String baseDate = nowMinusTenMinutes.format(dateFormatter);
        String baseTime = nowMinusTenMinutes.format(timeFormatter);

//		참고문서에 있는 url주소
        String apiUrl = "hhttp://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?ServiceKey=OYs%2FoOX5Znf7dF%2BiW%2FInedCkW%2FP0EdJ0wKF6vaqg%2BvyEsf6pmRwwgtwnCiI3Mkz2ky1UN%2FMN0zxjBO9eAsrHjQ%3D%3D&pageNo=1&numOfRows=1000&dataType=JSON";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8")); //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());
        log.info(url.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        String result= sb.toString();

        //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//

        // JSON 파싱 시작
        JSONObject jsonObj = new JSONObject(result);
        JSONObject response = jsonObj.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        // "category": "PTY"의 "obsrValue" 찾기
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            if ("PTY".equals(category)) {
                String obsrValue = item.getString("obsrValue");
                return !("0".equals(obsrValue)); // PTY가 0이라면 true
            }
        }

        // PTY가 없는 경우, false 반환
        return false;
    }
}
