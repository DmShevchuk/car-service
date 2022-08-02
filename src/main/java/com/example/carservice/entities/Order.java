package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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


    @Column(name = "appointment_date")
    private Date appointmentDate;

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

