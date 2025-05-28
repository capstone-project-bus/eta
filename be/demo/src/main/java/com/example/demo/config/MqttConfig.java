package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

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

    // 객체 주입을 위함
    @Autowired
    private ESPRepository espRepository;


	
	@Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler mqttMessageHandler(ObjectMapper objectMapper) {
        return message -> {
            try {
                String payload = message.getPayload().toString();
                System.out.println("📥 MQTT 메시지 수신: " + payload);

                if (payload.startsWith("{")) {
                    // payload는 String 타입이기 때문에 ESP32 객체로 변경 후 매핑
                    // 센서 데이터
                    ESP32 esp32 = objectMapper.readValue(payload, ESP32.class);
                    this.espRepository.save(esp32);

                    // gps 데이터 
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
