package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import com.example.demo.service.StationArrivalService;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationArrivalController {
	private final StationArrivalService stationArrivalService;
	
	// POST API 
	// StationArrivalService에서 사용 중 > 역에 도착했는지 체크 
	@PostMapping("/check-arrival")
	public ResponseEntity<String> checkArrival(
		// @RequestParam == request.getParameter의 기능과 동일 
		@RequestParam String busId,
		@RequestParam double lat,
		@RequestParam double lng
	){
		stationArrivalService.checkArrival(busId, lat, lng);
		return ResponseEntity.ok("역 도착 체크 완료");
	}
	
}
