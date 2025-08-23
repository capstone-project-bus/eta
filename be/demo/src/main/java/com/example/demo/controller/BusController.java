package com.example.demo.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.comp.DistanceHandler;
import com.example.demo.comp.SpeedHandler;
import com.example.demo.service.BusService;

import lombok.RequiredArgsConstructor;


// 클라이언트 요청 처리 REST API 컨트롤러 
// start 위치와 (gps module) 클라이언트(naver api)가 준 goal 위치를 이용해 ETA를 조회 (맞나여)

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")  // React 개발 서버 허용

public class BusController {
	private final BusService busService;
	private final DistanceHandler distancehdlr;
	private final SpeedHandler speedhdlr;
	private final BusLocationHandler busLocation;


        
       
	
	//naver directions 5 API 응답 body 받아옴
	@GetMapping
	public ResponseEntity<String> getEta(@RequestParam(value="start") String start, @RequestParam(value="goal") String goal){
		return ResponseEntity.ok(busService.getApi(start, goal));
	}
	
//	@GetMapping("/test")
//	public double test() {
//		String lng = busLocation.getLng();
//        String lat = busLocation.getLat();
//		return speedhdlr.getLiveEta(lng, lat);
//	}
	
	
	// BusService로부터 cofig의 count 값을 받아 fe로 리턴 
	@GetMapping("/bus/{id}/count")
	public Map<String, Object> getBusCount(@PathVariable("id") Long id){ // PathVariable: url 경로의 변수 값을 메서드 파라미터로 바인딩 
		Map<String, Object> result = new HashMap<>();
		result.put("count", busService.getCount(id));
		return result; // json 리턴 
	}
}
