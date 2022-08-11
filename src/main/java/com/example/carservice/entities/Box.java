package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
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
    private LocalTime startWorkTime;

    @Column(name = "end_time")
    private LocalTime endWorkTime;

    @Column(name = "time_factor")
    private Float timeFactor;

    @Column(name = "twenty_four_hour")
    private Boolean twentyFourHour = false;

    @OneToMany(mappedBy = "box")
    @Fetch(FetchMode.JOIN)
    private Set<Order> orders = new HashSet<>();
}
