package com.example.carservice.repos;

import com.example.carservice.entities.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepo extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findPasswordResetByToken(String token);
}
