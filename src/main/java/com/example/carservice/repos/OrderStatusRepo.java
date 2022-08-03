package com.example.carservice.repos;

import com.example.carservice.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {
    OrderStatus findOrderStatusesByStatusName(String statusName);

    boolean existsByStatusName(String statusName);
}
