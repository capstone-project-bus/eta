package com.example.demo.controller;

import java.net.URI;

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

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusController {
	private final BusService busService;

	// @GetMapping("/seats/{busId}")
	// public int getSeats(@PathVariable String busId){
    //     return mqttService.getSeats(busId);
    // }
	
	@Value("${map.api-id}")
	private String apiId;
	
	@Value("${map.api-key}")
	private String apiKey;
	
	//naver directions 5 API 응답 body 받아옴
	@GetMapping
	public ResponseEntity<String> getEta(@RequestParam(value="goal") String goal){
		
		String start = busService.getLocation();
		System.out.println(start);
		
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
		
		return ResponseEntity.ok(res.getBody());
	}
}
