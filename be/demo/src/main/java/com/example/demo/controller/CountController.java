package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.comp.BusLocationHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CountController {
	// Front가 API 요청하면 COUNT만 응답 
	// 개별 테스트용입니다 
	
	private final BusLocationHandler busLocation;
	
	@GetMapping("/api/count")
	public int getCurrentCOunt() {
		Map<String, Object> location = busLocation.getLocation();
		Object count = location.get("ppl");
		return count != null ? (int)count : 0;
	}
}
