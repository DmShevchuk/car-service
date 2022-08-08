package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "total_price")
    private Float totalPrice;

    @ManyToOne
    @JoinColumn(name = "id_of_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_of_box")
    private Box box;

    @ManyToOne
    @JoinColumn(name = "id_of_service_type")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "id_of_status")
    private OrderStatus orderStatus;
}

