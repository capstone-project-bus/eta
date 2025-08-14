package com.example.demo.comp;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.payload.BusRoute;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RouteHandler {
	private final ObjectMapper objectMapper;
	
	//아래에 route를 판별하는 메서드 작성해야 함. ~> 하갱이꺼 가져다 써야딤
	
	//BusRoute class에 맞게 /routes 밑의 파일을 읽어오기 (파일명이 매개변수)
	public BusRoute getRoute(String filename) throws IOException{
		ClassPathResource resource = new ClassPathResource("routes/" + filename);
		return objectMapper.readValue(resource.getInputStream(), BusRoute.class);
	}
}
