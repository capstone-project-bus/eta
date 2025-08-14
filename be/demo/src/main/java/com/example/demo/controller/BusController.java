package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.comp.DistanceHandler;
import com.example.demo.comp.SpeedHandler;
import com.example.demo.service.BusService;

import lombok.RequiredArgsConstructor;

// 클라이언트 요청 처리 REST API 컨트롤러 
// start 위치와 (gps module) 클라이언트(naver api)가 준 goal 위치를 이용해 ETA를 조회 (맞나여)

@RestController
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusController {
	private final BusService busService;
	private final DistanceHandler distancehdlr;
	private final SpeedHandler speedhdlr;
	private final BusLocationHandler busLocation;

	// @GetMapping("/seats/{busId}")
	// public int getSeats(@PathVariable String busId){
    //     return mqttService.getSeats(busId);
    // }

        
       
	
	//naver directions 5 API 응답 body 받아옴
	@GetMapping
	public ResponseEntity<String> getEta(@RequestParam(value="start") String start, @RequestParam(value="goal") String goal){
		return ResponseEntity.ok(busService.getApi(start, goal));
	}
	
	@GetMapping("/test")
	public double test() {
		String lng = busLocation.getLng();
        String lat = busLocation.getLat();
		return speedhdlr.getLiveEta(lng, lat);
	}
}
