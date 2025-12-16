package com.example.demo.comp;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.payload.eta.BusRoute;
import com.example.demo.payload.eta.Coordinate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DistanceHandler {
	private final RouteHandler routeHandler;
	
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
