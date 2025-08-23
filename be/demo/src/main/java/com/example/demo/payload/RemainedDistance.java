package com.example.demo.payload;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class RemainedDistance {
	private double remainedDstc;
	private List<Double> speedsInAMin = new LinkedList<>();
	private double eta;
}