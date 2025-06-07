package com.example.demo.config;
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

import com.example.demo.payload.BusPayload;
import com.example.demo.payload.GpsPayload;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;


@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String mqttUrl;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic}")
    private String[] topic;

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
                System.out.println("MQTT 메시지 수신: " + payload);

                // mqtt_receivedTopic: 스프링 Integration MQTT에서 자동으로 메시지에 붙여주는 헤더 키
                String receivedTopic = message.getHeaders().get("mqtt_receivedTopic").toString();
                if (receivedTopic.equals("esp32/ppl")) {

                    BusPayload busData = objectMapper.readValue(payload, BusPayload.class);
                    System.out.println("count: "+ busData.getCount());

                } else if (receivedTopic.equals("esp32/gps")){
                    GpsPayload gpsData = objectMapper.readValue(payload, GpsPayload.class);
                    System.out.println("위치: "+ gpsData.getLat() + "," + gpsData.getLng());
                }

            } catch (Exception e) {
                System.err.println("❌ JSON 파싱 실패: " + e.getMessage());
            }
        };
    }
}