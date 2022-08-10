package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_of_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_of_box")
    private Box box;

    @OneToOne
    @JoinColumn(name = "id_of_discount")
    private Discount discount;
}
