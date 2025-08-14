package com.example.demo.comp;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.payload.RemainedDistance;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpeedHandler {
	private final DistanceHandler distancehdlr;
	RemainedDistance remainedDstc2 = new RemainedDistance();
	
	
	public double getLiveEta(String lng, String lat) {	
		double movedDstc = 0.0;
		double beforeTenSec = remainedDstc2.getRemainedDstc();
		List<Double> speedsInAMin = remainedDstc2.getSpeedsInAMin();
		
		int currentLocation = distancehdlr.getIndex(lng, lat); //put gps data in params
		int remainedDstc = distancehdlr.getDistance(currentLocation);
		
		
		double avgSpeed = 0;
		
		System.out.println("test : " + beforeTenSec + ", " + remainedDstc);
		
		if(beforeTenSec == 0) {
			remainedDstc2.setRemainedDstc(remainedDstc);
			System.out.println("ETA(m/s, if) : " + remainedDstc / (15 * 1000 / 3600.0));
			return remainedDstc / (15 * 1000 / 3600.0);
		}
		else {
			movedDstc = beforeTenSec - remainedDstc;
			remainedDstc2.setRemainedDstc(remainedDstc);
			
			speedsInAMin.add(movedDstc / 10.0);
			System.out.println(speedsInAMin.size());
			
			if(speedsInAMin.size() <= 6) {
				int checker = 0;
				for(int i = 0; i < speedsInAMin.size(); i++) {
					avgSpeed += speedsInAMin.get(i);
					checker++;
					System.out.println("loop : " + avgSpeed);
				}
				avgSpeed /= checker;
				checker = 0;
				System.out.println("avg : " + avgSpeed);
				System.out.println("ETA(m/s, underAvg) : " + remainedDstc / avgSpeed);
				return remainedDstc / avgSpeed;
			}
			
			if(speedsInAMin.size() > 6) {
				speedsInAMin.remove(0);
				for(int i = 0; i < speedsInAMin.size(); i++) {
					avgSpeed += speedsInAMin.get(i);
					System.out.println("loop : " + avgSpeed);
				}
				avgSpeed /= 6;
				System.out.println("avg(end) : " + avgSpeed);
				System.out.println("ETA(m/s, avg) : " + remainedDstc / avgSpeed);
				return remainedDstc / avgSpeed;
			}
			
			System.out.println("Somehow I reached here");
			return remainedDstc / (movedDstc / 10.0);
		}
	}
}
