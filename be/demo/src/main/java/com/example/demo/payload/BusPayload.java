package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusPayload {
	// @Id
	// private int id;
	// 사용하지 않고 있는 데이터가 있으니까 파싱이 안 돼서 잠깐 주석 처리했습니당 
	private int count;
	private double lat;
	private double lon;
	// private String timestamp;
}
