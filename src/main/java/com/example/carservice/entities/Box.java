package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "boxes")
@Getter
@Setter
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    @Column(name = "box_name")
    private String name;

    @Column(name = "start_time")
    private Time startWorkTime;

    @Column(name = "end_time")
    private Time endWorkTime;

    @Column(name = "time_factor")
    private Float timeFactor;

    @OneToMany(mappedBy = "box")
    private Set<Order> orders = new HashSet<>();
}
