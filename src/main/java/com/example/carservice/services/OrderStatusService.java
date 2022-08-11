package com.example.carservice.services;

import com.example.carservice.entities.OrderStatus;
import com.example.carservice.exceptions.entities.OrderStatusNotFoundException;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.repos.OrderStatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Сервис для работы со статусами заказов {@link com.example.carservice.entities.enums.OrderStatusEnum}
 * */
@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepo orderStatusRepo;


    /**
     * Получение статуса заказа по строке
     * */
    public OrderStatus getOrderStatusByName(String name) {
        return orderStatusRepo.findOrderStatusesByStatusName(name)
                .orElseThrow(() -> new OrderStatusNotFoundException(name));
    }
}
