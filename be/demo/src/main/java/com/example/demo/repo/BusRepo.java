package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Bus;

@Repository
public interface BusRepo extends JpaRepository<Bus, Integer>{
	// Optional<Bus> findById(int id);
	Bus findById(String busId);
}

/* 
 * JpaRepository 를 상속받음.
 * Generic type 으로 받는 건 각각 <Entity, PK type> 이므로 <Bus, Integer>으로 설정.
 * Optional<T> 을 return 타입으로 설정하므로써, 반환값이 null 값이어도 오류 발생하지 않음. *(null의 wrapper class가 Optional)
 */