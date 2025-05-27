package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.example.demo.payload.BusPayload;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MqttConfig {
	@Value("${mqtt.url}")
	private String mqttUrl;
	
	@Value("${mqtt.client-id}")
	private String clientId;
	
	@Value("${mqtt.topic}")
	private String topic;
	
	@Bean
    public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}
	
	@Bean
	public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
       MqttPahoMessageDrivenChannelAdapter adapter =
       		new MqttPahoMessageDrivenChannelAdapter(mqttUrl, clientId, topic);
       adapter.setOutputChannel(mqttInputChannel());
       adapter.setCompletionTimeout(5000);
       adapter.setConverter(new DefaultPahoMessageConverter());
       adapter.setQos(1);
       return adapter;
    }
	
	@Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler mqttMessageHandler(ObjectMapper objectMapper) {
        return message -> {
            try {
                String payload = message.getPayload().toString();
                System.out.println("📥 MQTT 메시지 수신: " + payload);

                if (payload.startsWith("{")) {
                    BusPayload busData = objectMapper.readValue(payload, BusPayload.class);
                    System.out.println("👥 count: " + busData.getCount());
                    System.out.println("📍 위치: " + busData.getLat() + ", " + busData.getLon());
                } else {
                    System.out.println("⚠️ 단순 count 메시지: " + payload);
                }

            } catch (Exception e) {
                System.err.println("❌ JSON 파싱 실패: " + e.getMessage());
            }
        };
    }
}
