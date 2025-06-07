package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT) //null 값이면 JSON 형태의 데이터로 파싱되지 않음. dev tool에서 null값도 확인하고 싶다면 해당 줄 주석처리 요망.
@Table(name = "shuttle")	//local database 에 생성해둔 테이블명과 동일히 입력할 것
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, updatable = false)	//id, primary key
	private int id;
	private String busId;
	private int time;
	private double lat;
	private double lon;
	private byte ppl;
	
	//필드명과 컬럼명이 같기에 @Column 으로 굳이 명시 안해도 적용됨. (예외, PK ~> 옵션 지정 위함)
}
