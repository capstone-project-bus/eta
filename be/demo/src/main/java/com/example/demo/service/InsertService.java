package com.example.demo.service;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Sensor;
import com.example.demo.repo.BusRepo;
import com.example.demo.repo.GpsRepository;
import com.example.demo.repo.SensorRepository;

@Service
public class InsertService {
	@Autowired
	private SensorRepository sensorRepository;
	private GpsRepository gpsRepository;
	private BusRepo busRepo;
	
	public void addSensor(String busNum, int count, Byte status, LocalDateTime time) {
		
		// 도착했는지 판별 후에..
		
		Sensor sensor = new Sensor();
		sensor.setBusNum(busNum); // ex
		sensor.setCount(count);
		sensor.setStatus(status != null ? status : (byte)1);
		sensor.setTime(time != null ? time : LocalDateTime.now());
		
		//sensorRepository.save(sensor);
		
	}
}
