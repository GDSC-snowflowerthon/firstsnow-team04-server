package com.dev.firstsnow.Aministration;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocationMap {
    public Map<String, PointXY> map = new HashMap<>();
    public LocationMap(){
        map.put("서울특별시", new PointXY(60, 127));
        map.put("강원도", new PointXY(79, 125));
        map.put("경기도", new PointXY(59, 135));
        map.put("경상남도", new PointXY(89, 74));
        map.put("경상북도", new PointXY(83, 86));
        map.put("광주광역시", new PointXY(57, 74));
        map.put("대구광역시", new PointXY(89, 90));
        map.put("대전광역시", new PointXY(67, 100));
        map.put("부산광역시", new PointXY(100, 75));
        map.put("세종특별자치시", new PointXY(65, 103));
        map.put("울산광역시", new PointXY(102, 85));
        map.put("이어도", new PointXY(28, 8));
        map.put("인천광역시", new PointXY(55, 126));
        map.put("전라남도", new PointXY(50, 66));
        map.put("전라북도", new PointXY(63, 88));
        map.put("제주특별자치도", new PointXY(53, 38));
        map.put("충청남도", new PointXY(63, 103));
        map.put("충청북도", new PointXY(71, 114));
    }
}
