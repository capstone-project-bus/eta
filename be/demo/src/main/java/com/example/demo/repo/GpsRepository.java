package com.example.demo.repo;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Gps;
import com.example.demo.entity.Sensor;

public interface GpsRepository extends JpaRepository<Gps, LocalDateTime> {
	Optional<Sensor> findTopByBusNumOrderByTimeDesc(String busNum);

	
}
