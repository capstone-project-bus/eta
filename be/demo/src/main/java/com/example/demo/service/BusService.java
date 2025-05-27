package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repo.BusRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BusService {
	private final BusRepo busRepo;
}
