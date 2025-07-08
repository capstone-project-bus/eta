package com.example.demo.config;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.example.demo.comp.BusLocationHandler;
import com.example.demo.payload.BusPayload;
import com.fasterxml.jackson.databind.ObjectMapper;


// MQTT 메시지를 수신해서 처리
// JSON -> Map -> (향후) DTO Class -> DB

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

    //MQTT 통해 받아온 msg parsing
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler mqttMessageHandler(ObjectMapper objectMapper, BusLocationHandler busLocation) {
        return message -> {
            try {
                String payload = message.getPayload().toString();
                System.out.println("MQTT 메시지 수신: " + payload);

                // mqtt_receivedTopic: 스프링 Integration MQTT에서 자동으로 메시지에 붙여주는 헤더 키
                String receivedTopic = message.getHeaders().get("mqtt_receivedTopic").toString();
                if (receivedTopic.equals("esp32/ppl")) {
                    // Map으로 수정 
                    // JSON을 Map 형태로 파싱 
                    // 사용 방법 ex. pplData.get("count") -> 12
                    Map<String, Object> pplData = objectMapper.readValue(payload, Map.class);
                    // location: lat, lng, time, ppl 등 저장하는 Map (맞져?)
                    Map<String, Object> location = busLocation.getLocation();
                    // count를 Map에 ppl이라는 이름으로 저장
                    location.put("ppl", pplData.get("count"));
                    // System.out.println(location.get("count"));

                    

                } else if (receivedTopic.equals("esp32/gps")){
                	Map<String, Object> gpsData = objectMapper.readValue(payload, Map.class);
                	
                	Map<String, Object> location = busLocation.getLocation();
                	location.put("lat", gpsData.get("LAT"));
        			location.put("lng", gpsData.get("LONG"));
        			location.put("date", gpsData.get("DATE"));
        			location.put("time", gpsData.get("TIME"));	//time, UTC. 우리나라는 UTC+9 이므로 로직 짤 때 유의할 것
        			
        			System.out.println(location.get("date"));
        			System.out.println(location.get("time"));
        			System.out.println(location.get("lat"));
        			System.out.println(location.get("lng"));
        			System.out.println("----------------------------");
                }

            } catch (Exception e) {
                System.err.println("❌ JSON 파싱 실패: " + e.getMessage());
            }
        };
    }
}