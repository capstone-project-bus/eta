package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.repo.BusRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
