package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "service_type")
@Getter
@Setter
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id")
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "price")
    private Long price;
}
