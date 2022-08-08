package com.example.carservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_resets")
@Getter
@Setter
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reset_id")
    private Long id;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "token")
    private String token;

    @Column(name = "new_password")
    private String newPassword;

    @OneToOne
    @JoinColumn(name = "id_of_user")
    private User user;
}
