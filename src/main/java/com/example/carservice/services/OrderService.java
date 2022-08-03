package com.example.carservice.services;

import com.example.carservice.entities.Order;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public Order create(Order order){
        return orderRepo.save(order);
    }

    public Order update(Long id, Order order){
        order.setId(id);
        return orderRepo.save(order);
    }

    public Page<Order> getAll(Pageable pageable){
        return orderRepo.findAll(pageable);
    }

    public void remove(Long id){
        orderRepo.deleteById(id);
    }

    public Order getOrderById(Long id){
        return orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
    }
}
