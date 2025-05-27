package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.BusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {
	private final BusService busService;
}
