package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "confirmations")
@Getter
@Setter
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirmation_id")
    private Long id;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "id_of_order")
    private Order order;
}
