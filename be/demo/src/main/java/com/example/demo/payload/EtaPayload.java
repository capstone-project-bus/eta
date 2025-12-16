package com.example.demo.payload;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.payload.eta.BusRoute;
import com.example.demo.payload.eta.RemainedDistance;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class EtaPayload {
	private Map<String, RemainedDistance> liveBusPayload = new HashMap<>();
	
	public boolean isTherePrevInfo(String busNum) {
		return liveBusPayload.containsKey(busNum);
	}
	
	public RemainedDistance getPreviousBusInfo(String busNum) {
		return liveBusPayload.get(busNum);
	}
	
	public void updateBusInfo(String busNum, RemainedDistance remainedDistance) {
		liveBusPayload.put(busNum, remainedDistance);
	}
}
