package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
	@Value("${mqtt.url}")
	private String mqttUrl;
	
	@Value("${mqtt.client-id}")
	private String clientId;
	
	@Value("${mqtt.topic}")
	private String topic;
	
}
