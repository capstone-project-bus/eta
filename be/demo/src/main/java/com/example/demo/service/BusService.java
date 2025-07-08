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
	public Map<String, Integer> getApi(String start, String goal, String waypoints) {
//		String start = getLocation();
//		System.out.println(start);
		
		String url = String.format(
				"https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=%s&goal=%s&waypoints=%s", 
				start, goal, waypoints);
		
		URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
		
		RequestEntity<Void> req = RequestEntity
				.get(uri)
				.header("X-NCP-APIGW-API-KEY-ID", apiId)
				.header("X-NCP-APIGW-API-KEY", apiKey)
				.build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(req, String.class);
		
		System.out.println(parseApiData(res.getBody()));
		System.out.println("----------------------");
		return parseApiData(res.getBody());
//		return res.getBody();
	}
	
	//API로부터 받아온 Json 형태의 응답 body(String)를 Json으로 parsing 후, Map 객체에 파싱된 데이터 저장 로직
	public Map<String, Integer> parseApiData(String respondBody){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode respondBodyJson = objectMapper.readTree(respondBody);
			
			JsonNode summary = respondBodyJson.path("route").path("traoptimal").get(0).path("summary");
			JsonNode guide = respondBodyJson.path("route").path("traoptimal").get(0).path("guide");
			
	        Map<String, Object> parsedSummary = new HashMap<>();
	        parsedSummary.put("distance", summary.path("distance").asInt());
	        parsedSummary.put("duration", summary.path("duration").asInt());
	        
	        List<Map<String, Object>> parsedGuide = new ArrayList<>();
	        for (JsonNode guideNode : guide) {
	            Map<String, Object> guideItem = new HashMap<>();

	            guideItem.put("instructions", guideNode.path("instructions").asText());
	            
	            // 선택적: 거리/시간 포함되어 있다면
	            if (guideNode.has("distance")) {
	                guideItem.put("distance", guideNode.path("distance").asInt());
	            }
	            if (guideNode.has("duration")) {
	                guideItem.put("duration", guideNode.path("duration").asInt());
	            }

	            parsedGuide.add(guideItem);
	        }
	        
	        Map<String, Integer> parsedData = new HashMap<>();
	        
	        int dis = 0, drt = 0, pointCheck = 0;
	        
	        for(int i = 0; i < parsedGuide.size(); i++) {
	        	dis += (Integer)parsedGuide.get(i).get("distance");
	        	drt += (Integer)parsedGuide.get(i).get("duration");
	        	
	        	if("경유지".equals(parsedGuide.get(i).get("instructions")) && pointCheck == 0) {
	        		parsedData.put("sec1Distance", dis);
	        		parsedData.put("sec1Duration", drt);
	        		dis = 0;
	        		drt = 0;
	        		pointCheck++;
	        		continue;
	        	}
	        	else if("경유지".equals(parsedGuide.get(i).get("instructions")) && pointCheck == 1) {
	        		parsedData.put("sec2Distance", dis);
	        		parsedData.put("sec2Duration", drt);
	        		dis = 0;
	        		drt = 0;
	        		continue;
	        	}
	        	else if("목적지".equals(parsedGuide.get(i).get("instructions"))) {
	        		parsedData.put("sec3Distance", dis);
	        		parsedData.put("sec3Duration", drt);
	        		break;
	        	}
	        }
	        
	        return parsedData;
	        
		}catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}
}
