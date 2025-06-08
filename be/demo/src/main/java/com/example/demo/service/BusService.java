package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.repo.BusRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 비즈니스 로직 담당 파일 
// getSeats: 향후 좌석 수 계산 
// getLocation: 위치 정보를 가져옴 

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BusService {
	private final BusRepo busRepo;
	private final BusLocationHandler busLocationHdlr;

	// public int getSeats(String busId){
	// 	Bus bus = BusRepo.findById(busId);
	// 	return 33 - bus.getPpl();
	// }
	
	public String getLocation() {
        String lng = busLocationHdlr.getLng();
        String lat = busLocationHdlr.getLat();
        
        return lng + "," + lat;
    }
}
