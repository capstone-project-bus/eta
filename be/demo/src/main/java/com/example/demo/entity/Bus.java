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
@Table(name = "businfo")	//버스 정보 테이블명입니다 
public class Bus {
	@Id
	@Column(name="bus_num", length=50) // varchar(50) pk
	private String busNum;
	
	@Column(name="bus_root", length=50) // varchar(50)
	private String busRoot;
	
}
