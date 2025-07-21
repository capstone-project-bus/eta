package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import com.example.demo.service.StationArrivalService;


@RestController
@RequestMapping("api/station") // (get, post 허용) default: get
@RequiredArgsConstructor // 생성자를 따로 안 적어도 되게 해주는 녀석.. 
public class StationArrivalController {
	private final StationArrivalService stationArrivalService;
	
	@PostMapping("/check-arrival") // (post만 허용 == RestAPI)
	public ResponseEntity<String> checkArrival(
		@RequestParam String busId,
		@RequestParam double lat, 
		@RequestParam double lng){
		// @RequestParam == request.getParameter의 기능과 동일 
		boolean arrived = stationArrivalService.checkArrival(busId, lat, lng);
		
		if(arrived) {
			stationArrivalService.processArrival("역: ", busId);
		}
		return ResponseEntity.ok(arrived ? "도착":"");
	}
		

}
