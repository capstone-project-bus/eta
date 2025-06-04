package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.BusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusController {
	private final BusService busService;

	// @GetMapping("/seats/{busId}")
	// public int getSeats(@PathVariable String busId){
    //     return mqttService.getSeats(busId);
    // }
}
