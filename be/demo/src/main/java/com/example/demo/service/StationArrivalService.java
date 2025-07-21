package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;    
import java.util.Map; 

public class StationArrivalService {
	// 고정 역 좌표 (도착지)
	private static final Map<String, List<Double>> STATION_COORDS = Map.of(
			"한양대역", Arrays.asList(37.554548, 127.043719),
			"왕십리역", Arrays.asList(37.560938, 127.038060),
	        "마장역", Arrays.asList(37.566083, 127.043052),
	        "한양여자대학교", Arrays.asList(37.558525, 127.050777)
	);
	
	
	// 역 도착 여부 판단 
	public boolean checkArrival(String busId, double busLat, double busLng) {
		for (Map.Entry<String, List<Double>> entry: STATION_COORDS.entrySet()) {
			
			// 변수 선언 
			String station = entry.getKey(); // ex. "왕십리역"
			List<Double> coords = entry.getValue(); // ex. [위도, 경도]
			
			// 요청으로 받은 busId와 위도, 경도(esp32) 값과 Map에 저장되어 있는 고정 역 좌표를 비교 
			double stationLat = coords.get(0); // 고정 위도 
			double stationLng = coords.get(1); // 고정 경도 
			
			double distance = calculateDistanceMeters(
					stationLat, stationLng, // 고정 위도 경도,
					busLat, busLng ); // esp32 위도 경도
			
			if(distance < 20) { // 사이의 거리가 20m 미만이라면 
				processArrival(station, busId); // -------- 도착으로 판단------------------------!! 
				return true; // 도착 == true 
			}
			
		}
		return false; // 운행 중 == false 
				
	}
	
	public static final String SENSOR_START_COMMAND = "START";
	private ESP32Client esp32Client;
	public void processArrival(String station, String busId) {
		// 도착이라 판단된 경우 수행되어야 할 과제: 적외선 센서 실행 
		// HOW? esp32에 "적외선 센서 카운트 시작해라" 게시 
		
		esp32Client.sendStartCommand(busId); 
		// 통신과 서비스를 분리하기 위해 ESP32Client.java에 작성함
		// MqttConfig > 수신용 
		// ESP32Client > 전송용 
		
		
	}
	
	
	// 고정 역과 현재 위도, 경도 거리 계산 로직 
	public double calculateDistanceMeters(
			double lat1, double lng1,
			double lat2, double lng2) {
		// 둥근 지구... 곡률에 영향을 받음 
		// 하버사인 공식 코드 (검색 시 나옴) 활용 
		
		final int EARTH_RADIUS = 6371000; // 지구 반지름 
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(dLng / 2) * Math.sin(dLat / 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS * c;
	}
	
	}

