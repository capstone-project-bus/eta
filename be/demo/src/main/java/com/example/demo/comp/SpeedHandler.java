package com.example.demo.comp;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.payload.EtaPayload;
import com.example.demo.payload.eta.RemainedDistance;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpeedHandler {
	private final DistanceHandler distancehdlr;
	private final RouteHandler routeHdlr;
	
	private final EtaPayload etaPayload;
	//private final RemainedDistance remainedDstc2; //171225, 주석처리
	
	public double getLiveEta(String busNum, String lng, String lat) {	
		double movedDstc = 0.0;
		RemainedDistance remainedDstc2;
		double beforeTenSec;
		List<Double> speedsInAMin;
		
		if(etaPayload.isTherePrevInfo(busNum)) {
			remainedDstc2 = etaPayload.getPreviousBusInfo(busNum);
			beforeTenSec = remainedDstc2.getRemainedDstc();
			speedsInAMin = remainedDstc2.getSpeedsInAMin();
		}
		else {
			remainedDstc2 = new RemainedDistance();
			beforeTenSec = 0;
			speedsInAMin = remainedDstc2.getSpeedsInAMin();
		}
		//double beforeTenSec = remainedDstc2.getRemainedDstc();
		//List<Double> speedsInAMin = remainedDstc2.getSpeedsInAMin();
		
		//coord[] : {current location, next waypoint} (if next waypoint == destination, it's 0)
		int[] coord = routeHdlr.getIndex(lng, lat); //put gps data in params
		int remainedDstc = distancehdlr.getDistance(coord[0]);
		if(coord[1] != 0) {
			remainedDstc += distancehdlr.getDistance(coord[1]);
		}
		//For now, just adding up but next time I should divide it (251025)
		//or just subtract the eta from the whole remained eta
		
		System.out.println("test : " + beforeTenSec + ", " + remainedDstc);
		
		if(speedsInAMin.isEmpty()) {		//출발지에서 출발(초기값 세팅)
			remainedDstc2.setRemainedDstc(remainedDstc);
			System.out.println("ETA(m/s, if) : " + remainedDstc / (15 * 1000 / 3600.0));
			remainedDstc2.setEta(remainedDstc / (15 * 1000 / 3600.0));
			etaPayload.updateBusInfo(busNum, remainedDstc2);
			return remainedDstc2.getEta();
		}
		else {	//운행 중 eta 값 계산
			movedDstc = beforeTenSec - remainedDstc;
			
			//밑에 테스트 해봐야함. ㅆㅂ fuck fuck fuck
			//정지 및 막힘 여부 확인 -> 10초간 정지 혹은 3m의 이동도 없었을 경우 기존 eta 값에 10초 추가
			if((movedDstc) < 3.0) {
				remainedDstc2.setRemainedDstc(remainedDstc);
				System.out.println("ETA(m/s, stuck) : " + (remainedDstc2.getEta() + 10.0));
				remainedDstc2.setEta(remainedDstc2.getEta() + 10.0);
				etaPayload.updateBusInfo(busNum, remainedDstc2);
				return remainedDstc2.getEta();
			}
			
			remainedDstc2.setRemainedDstc(remainedDstc);
			
			speedsInAMin.add(movedDstc / 10.0);
			System.out.println(speedsInAMin.size());
			
			//운행 시작 후 1분 미만, 평균 초속값을 구하기 위해 데이터 쌓으며 eta 값 예측
			if(speedsInAMin.size() <= 6) {
				int checker = 0;
				double avgSpeed = 0;
				for(int i = 0; i < speedsInAMin.size(); i++) {
					avgSpeed += speedsInAMin.get(i);
					checker++;
					System.out.println("loop : " + speedsInAMin.get(i));
				}
				avgSpeed /= checker;
				checker = 0;
				System.out.println("avg : " + avgSpeed);
				System.out.println("ETA(m/s, underAvg) : " + remainedDstc / avgSpeed);
				remainedDstc2.setEta(remainedDstc / avgSpeed);
				etaPayload.updateBusInfo(busNum, remainedDstc2);
				return remainedDstc2.getEta();
			}
			
			//운행 중 현 위치 기준 최근 1분 간의 평균 초속으로 eta 값 예측
			if(speedsInAMin.size() > 6) {
				speedsInAMin.remove(0);
				double avgSpeed = 0;
				for(int i = 0; i < speedsInAMin.size(); i++) {
					avgSpeed += speedsInAMin.get(i);
					System.out.println("loop : " + speedsInAMin.get(i));
				}
				avgSpeed /= 6.0;
				System.out.println("avg(end) : " + avgSpeed);
				System.out.println("ETA(m/s, avg) : " + remainedDstc / avgSpeed);
				remainedDstc2.setEta(remainedDstc / avgSpeed);
				etaPayload.updateBusInfo(busNum, remainedDstc2);
				return remainedDstc2.getEta();
			}
			
			System.out.println("Somehow I reached here");
			remainedDstc2.setEta(remainedDstc / (movedDstc / 10.0));
			etaPayload.updateBusInfo(busNum, remainedDstc2);
			return remainedDstc2.getEta();
		}
	}
}
