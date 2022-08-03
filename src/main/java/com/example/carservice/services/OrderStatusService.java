package com.example.carservice.services;

import com.example.carservice.entities.OrderStatus;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.exceptions.OrderStatusAlreadyExistsException;
import com.example.carservice.repos.OrderStatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepo orderStatusRepo;

    public OrderStatus add(OrderStatus orderStatus) {
        if (orderStatusRepo.existsByStatusName(orderStatus.getStatusName())) {
            throw new OrderStatusAlreadyExistsException(orderStatus.getStatusName());
        }
        return orderStatusRepo.save(orderStatus);
    }

    public OrderStatus update(Long id, OrderStatus orderStatus) {
        orderStatus.setId(id);
        return orderStatusRepo.save(orderStatus);
    }

    public Page<OrderStatus> getAll(Pageable pageable) {
        return orderStatusRepo.findAll(pageable);
    }

    public void remove(Long id) {
        orderStatusRepo.deleteById(id);
    }

    public OrderStatus getOrderStatusById(Long id) {
        return orderStatusRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order status", id));
    }
}
