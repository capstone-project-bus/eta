package com.example.demo.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ESP32Client {
	
//	private MqttClient mqttClient;
//	
//	public ESP32Client(
//			@Value("${mqtt.url}") String mqttUrl,
//			@Value("${mqtt.client-id") String clientId
//			) throws MqttException{
//		this.mqttClient = new MqttClient(mqttUrl, clientId + "-sender", new MemoryPersistence());
//		mqttClient.connect();
//	}
//	
//	public void sendStartCommand(String busId) {
//        String topic = "bus/" + busId + "/sensor";
//        try {
//            MqttMessage message = new MqttMessage("START".getBytes());
//            mqttClient.publish(topic, message);
//        } catch (MqttException e) {
//            e.printStackTrace(); // 통신 오류 로깅
//        }
//    }
}
