package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.service.BusService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

// 클라이언트 요청 처리 REST API 컨트롤러 
// start 위치와 (gps module) 클라이언트(naver api)가 준 goal 위치를 이용해 ETA를 조회 (맞나여)

@Controller
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusController {
	private final BusService busService;

	// @GetMapping("/seats/{busId}")
	// public int getSeats(@PathVariable String busId){
    //     return mqttService.getSeats(busId);
    // }
	
	//naver directions 5 API 응답 body 받아옴
	@GetMapping
	public ResponseEntity<Map<String, Integer>> getEta(@RequestParam(value="start") String start, @RequestParam(value="goal") String goal, @RequestParam(value="waypoints") String waypoints){
		return ResponseEntity.ok(busService.getApi(start, goal, waypoints));
	}
}
