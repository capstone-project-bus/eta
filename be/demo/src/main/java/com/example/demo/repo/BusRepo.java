package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Bus;

@Repository
public interface BusRepo extends JpaRepository<Bus, Integer>{
	 Optional<Bus> findById(int id);	//PK 기반 조회. Optional<Bus> 타입 설정 이유는 아래 주석 참고
	 
	 Bus findByBusId(String busId);		//기존 코드에서 위 코드 주석해제, findById로 설정시 기본제공되는 findById와 충돌가능하기에 이름 수정해둠
}

/* 
 * JpaRepository 를 상속받음.
 * Generic type 으로 받는 건 각각 <Entity, PK type> 이므로 <Bus, Integer>으로 설정.
 * Optional<T> 을 return 타입으로 설정하므로써, 반환값이 null 값이어도 오류 발생하지 않음. *(null의 wrapper class가 Optional)
 */