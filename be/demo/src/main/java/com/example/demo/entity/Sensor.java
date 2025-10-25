package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor") // 테이블명.. sensor
public class Sensor {
	
    @Column(name = "bus_num", length = 50) 
    private String busNum;

    @Column(name = "count")
    private Integer count;

    @Column(name = "status") 
    private Byte status;
    
    @Id
    @Column(name="time")
    private LocalDateTime time;
}
