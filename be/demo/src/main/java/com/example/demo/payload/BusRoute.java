package com.example.demo.payload;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRoute {
	private String routeName;
	private List<Coordinate> route;
}
