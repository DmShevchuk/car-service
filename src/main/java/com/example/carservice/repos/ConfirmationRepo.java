package com.example.carservice.repos;

import com.example.carservice.entities.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepo extends JpaRepository<Confirmation, Long>, JpaSpecificationExecutor<Confirmation> {
    Optional<Confirmation> getConfirmationByToken(String token);
}
