package com.example.carservice.repos;

import com.example.carservice.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findOrderStatusesByStatusName(String statusName);
}
