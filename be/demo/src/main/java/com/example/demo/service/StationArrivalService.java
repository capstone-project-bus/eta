package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class StationArrivalService {
	
	// 고정 역 위치 (도착지)
	private static final Map<String, List<Double>> STATION_COORDS = Map.of(
            "한양대역", Arrays.asList(37.554548, 127.043719),
            "왕십리역", Arrays.asList(37.560938, 127.038060),
            "마장역", Arrays.asList(37.566083, 127.043052),
            "한양여자대학교", Arrays.asList(37.558525, 127.050777)
    );
	
    // 각 역 도착 여부를 검사 후 콜백 함수 호출 
    public void checkArrival(String busId, double busLat, double busLng){
        STATION_COORDS.forEach((station, coords) -> {
            double stationLat = coords.get(0);
            double stationLng = coords.get(1);

            double distance = calculateDistanceMeters(
            		// stationLat, Lng == 고정 위치 
                    stationLat, stationLng,
                    // busLat, Lng == 현재 위치 (센서)
                    busLat, busLng
            );
            // 역과 버스 거리가 20미터 미만이면 도착 처리
            if (distance < 20){
            processArrival(station, busId);
            }
        });
    }

    private void processArrival(String station, String busId){
        System.out.printf("버스 [%s]가 [%s]에 도착.. 예?정? %n", busId, station);
    }

    
    // 지구 곡률의 영향 有 => 하버사인 공식 (위경도 좌표 사이의 거리 구할 때 사용)
    private double calculateDistanceMeters(
            double lat1, double lon1,
            double lat2, double lon2) {

    	// 지구 반지름 
        final int EARTH_RADIUS = 6371000; // meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
}
}
