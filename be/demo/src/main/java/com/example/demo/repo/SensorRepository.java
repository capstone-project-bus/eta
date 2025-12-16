package com.example.demo.repo;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, LocalDateTime> {
	Optional<Sensor> findTopByBusNumOrderByTimeDesc(String busNum);
	
	//171225, 채
	/*
	 * 위 라인에서 에러발생, 에러이유는 sementic error이고 파라미터가 잘못되어 발생한다고함.
	 * findTop : JPA에 결과값은 1개
	 * ByBusNum : Spring에 Where문을 요청하는 부분, 하여 sql문으로는 WHERE Sensor.busNum = ?이 됨
	 * OrderByTimeDesc : time을 오름차순으로 정렬
	 * 
	 * => 여기서 구문상으로 busNum이 넘어와야하여 String을 받을 것을 프로그램은 예상하나 예상과는 다른
	 * LocalDateTime Type이 들어와서 오류발생하는 것
	 * 하여 해당 부분 수정함
	 */
}

