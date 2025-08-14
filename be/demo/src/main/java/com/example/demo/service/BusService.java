package com.example.demo.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.repo.BusRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 비즈니스 로직 담당 파일 (버스 상태 관리)
// getSeats: 향후 좌석 수 계산 
// getLocation: 위치 정보를 가져옴 

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BusService {
	private final BusRepo busRepo;
	private final BusLocationHandler busLocationHdlr;

	// public int getSeats(String busId){
	// 	Bus bus = BusRepo.findById(busId);
	// 	return 33 - bus.getPpl();
	// }
	
	@Value("${map.api-id}")
	private String apiId;
	
	@Value("${map.api-key}")
	private String apiKey;
	
	//gps로 부터 받아온 버스 좌표값, API에 출발 지점 값으로 보내기 위함
	public String getLocation() {
        String lng = busLocationHdlr.getLng();
        String lat = busLocationHdlr.getLat();
        
        return lng + "," + lat;
    }
	
	//Naver Direction 5 API에 응답 Body 요청 로직
	public String getApi(String start, String goal) {
//		String start = getLocation();
//		System.out.println(start);
		
		String url = String.format(
				"https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=%s&goal=%s", 
				start, goal);
		
		URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
		
		RequestEntity<Void> req = RequestEntity
				.get(uri)
				.header("X-NCP-APIGW-API-KEY-ID", apiId)
				.header("X-NCP-APIGW-API-KEY", apiKey)
				.build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(req, String.class);
		
		return res.getBody();
	}
}
