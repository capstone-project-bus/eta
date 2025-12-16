package com.example.demo.comp;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.payload.eta.BusRoute;
import com.example.demo.payload.eta.Coordinate;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RouteHandler {
	private final ObjectMapper objectMapper;
	
	//버스 번호로 루트 판별해서 1차 거르기(두 경로가 겹치는 구간이 있을 수 있기에)
	//아래에 route(정류장-정류장)를 판별하는 메서드 작성해야 함. ~> 하갱이꺼 가져다 써야딤(StationArrivalService)
	//하여 checkArrival 을 가져다가 루트를 판별, return 문 이전에 station변수를 매개 변수로 갖는 메서드 호출하기, 해당 메서드를 여기에 작성하기
	
	//BusRoute class에 맞게 /routes 밑의 파일을 읽어오기 (파일명이 매개변수)
	//251025,
	//if we add another route json file
	//write a if statement to distinguish whether its Wang or Majang route w/ bus id
	public BusRoute getRoute(String filename) throws IOException{
		ClassPathResource resource = new ClassPathResource("routes/" + filename);
		return objectMapper.readValue(resource.getInputStream(), BusRoute.class);
	}
	
	public int[] getIndex(String Lng, String Lat) {
		int[] index = {0, 52};
//		int[] index = {0, 0};
		try {
			//251025,
//			if(getBusId() == 1) {
//				BusRoute busRoute = getRoute("Wang.json");
//				List<Coordinate> route = busRoute.getRoute();
			
//			}
//			if(getBusId() == 2) {
//				BusRoute busRoute = getRoute("Majang.json");
//				List<Coordinate> route = busRoute.getRoute();
//				int[1] = 52;
//			}
			BusRoute busRoute = getRoute("testRoute.json");
			List<Coordinate> route = busRoute.getRoute();
			
			double currentLng = Double.parseDouble(Lng);
			double currentLat = Double.parseDouble(Lat);
			
			double routeLng, routeLat;
			double minDstc = 0;
			for(int i = 0; i < route.size(); i++) {
				routeLng = route.get(i).getLng();
				routeLat = route.get(i).getLat();
				
				double dstc = Math.sqrt(Math.pow((routeLng - currentLng), 2) + Math.pow((routeLat - currentLat), 2));
				
				if(i == 0) {
					minDstc = dstc;
					continue;
				}
				
				if(dstc < minDstc) {
					minDstc = dstc;
					index[0] = i;
				}
				else continue;
			}
			
			if(index[0] < index[1]) index[1] = 52;
			else index[1] = 0;
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return index;
	}
}
