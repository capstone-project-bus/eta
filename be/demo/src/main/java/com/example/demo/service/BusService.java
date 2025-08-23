package com.example.demo.service;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.repo.BusRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 비즈니스 로직 담당 파일 (버스 상태 관리)

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
	
	private final RestTemplate restTemplate = new RestTemplate();

//	  // Runs every 30 seconds
//	@Scheduled(fixedRate = 10_000)
//	public void callApiEveryMinute() {
//	    String url = "http://localhost:8080/api/bus/test";
//	    String response = restTemplate.getForObject(url, String.class);
//	    System.out.println("API Response: " + response);
//	}
	
	
	// config 센서 데이터 처리
	private final Map<Long, Map<String, Object>> cache = new ConcurrentHashMap<>();
	public void updateLocation(Long busId, Map<String, Object> location) {
	    System.out.println("busId=" + busId + " data=" + location);

		cache.put(busId, location);
	}
	public Integer getCount(Long busId) {
	    Map<String, Object> location = cache.get(busId);
	    
	    if (location == null) return null;

	    Object val = location.get("ppl");
	    if (val == null) return null;

	    // 숫자가 Double/String/Integer 어떤 타입이든 안전하게 변환
	    if (val instanceof Number) {
	        return ((Number) val).intValue();   
	    } else {
	        return Integer.parseInt(val.toString());
	        
	    }
	}


}
