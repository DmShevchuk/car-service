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

    @Transactional
    public OrderStatus add(OrderStatus orderStatus) {
        if (orderStatusRepo.existsByStatusName(orderStatus.getStatusName())) {
            throw new OrderStatusAlreadyExistsException(orderStatus.getStatusName());
        }
        return orderStatusRepo.save(orderStatus);
    }

    @Transactional
    public OrderStatus update(Long id, OrderStatus orderStatus) {
        orderStatus.setId(id);
        return orderStatusRepo.save(orderStatus);
    }

    public Page<OrderStatus> getAll(Pageable pageable) {
        return orderStatusRepo.findAll(pageable);
    }

    @Transactional
    public void remove(Long id) {
        orderStatusRepo.deleteById(id);
    }

    public OrderStatus getOrderStatusById(Long id) {
        return orderStatusRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order status", id));
    }

    public OrderStatus getOrderStatusByName(String name) {
        return orderStatusRepo.findOrderStatusesByStatusName(name)
                .orElseThrow(() -> new OrderStatusNotFoundException(name));
    }
}
