package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusPayload {
	@Id
	private int id;
	private int count;
	private double lat;
	private double lon;
	private String timestamp;
}
