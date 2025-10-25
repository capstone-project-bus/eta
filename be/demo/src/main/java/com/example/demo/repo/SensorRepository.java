package com.example.demo.repo;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, LocalDateTime> {
	Optional<Sensor> findTopByBusNumOrderByTimeDesc(LocalDateTime time);
	
}

