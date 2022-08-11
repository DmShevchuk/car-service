package com.example.carservice.services;

import com.example.carservice.entities.OrderStatus;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.exceptions.OrderStatusAlreadyExistsException;
import com.example.carservice.exceptions.OrderStatusNotFoundException;
import com.example.carservice.repos.OrderStatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepo orderStatusRepo;

    public OrderStatus getOrderStatusByName(String name) {
        return orderStatusRepo.findOrderStatusesByStatusName(name)
                .orElseThrow(() -> new OrderStatusNotFoundException(name));
    }
}
