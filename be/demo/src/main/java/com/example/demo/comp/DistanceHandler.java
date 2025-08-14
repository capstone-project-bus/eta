package com.example.demo.comp;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.payload.BusRoute;
import com.example.demo.payload.Coordinate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DistanceHandler {
	private final RouteHandler routeHandler;
	
	public int getIndex(String Lng, String Lat) {
		int index = 0;
		try {
			BusRoute busRoute = routeHandler.getRoute("testRoute.json");
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
					index = i;
				}
				else continue;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return index;
	}
	
	public int getDistance(int index) {
		final int EARTH_RADIUS = 6371000;
		int dstc = 0;
		try {
			BusRoute busRoute = routeHandler.getRoute("testRoute.json");
			List<Coordinate> route = busRoute.getRoute();
			
			System.out.println(index);
			
			for(int i = index; i < (route.size()-1); i++) {
				double dLng = Math.toRadians(route.get(i+1).getLng() - route.get(i).getLng());
				double dLat = Math.toRadians(route.get(i+1).getLat() - route.get(i).getLat());
				
				double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
						   + Math.cos(Math.toRadians(route.get(i).getLat())) * Math.cos(Math.toRadians(route.get(i+1).getLat()))
						   * Math.sin(dLng / 2) * Math.sin(dLng / 2);
				
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

				dstc += Math.round(EARTH_RADIUS * c);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return dstc;
	}
}
