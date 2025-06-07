package com.example.demo.comp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BusLocationHandler {
	//GPS로 부터 받아온 좌표값을 저장할 HashMap 인스턴스
    private final Map<String, Object> location = new HashMap<>();

    //위 Map 인스턴스를 반환
    public Map<String, Object> getLocation() {
        return location;
    }

    //위 인스턴스에 Latitude 값 저장, default는 0.0
    public String getLat() {
        return location.getOrDefault("lat", "0.0").toString();
    }

    //위 인스턴스에 Longitude 값 저장, default는 0.0
    public String getLng() {
        return location.getOrDefault("lng", "0.0").toString();
    }
}
