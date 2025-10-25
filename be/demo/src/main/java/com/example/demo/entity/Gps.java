package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gps") // 테이블명.. gps

public class Gps {
	 	@Column(name = "bus_num", length = 50) 
	    private String busNum;

	    @Column(name = "lan")
	    private Character lan;

	    @Column(name = "lng") 
	    private Character lng;
	    
	    @Id
	    @Column(name="time")
	    private LocalDateTime time;
}
