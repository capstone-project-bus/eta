package com.example.demo.repo;

// 리포지토리로 만들기 위해 JpaRepository 상속
import org.springframework.data.jpa.repository.JpaRepository;

// ESP32: 엔티티 클래스
// Integer: 기본 키 타입
public interface ESPRepository extends JpaRepository<BusPayload ,Integer> {
}
