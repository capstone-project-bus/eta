package com.example.demo.payload;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemainedDistance {
	private double remainedDstc;
	private List<Double> speedsInAMin = new LinkedList<>();
}