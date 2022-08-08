package com.example.carservice.services;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.specification.impl.IncomeSpecificationFactoryImpl;
import com.example.carservice.specification.impl.OrderSpecificationFactoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderStatusService orderStatusService;
    private final IncomeSpecificationFactoryImpl incomeSpecificationFactory;
    private final OrderSpecificationFactoryImpl orderSpecificationFactory;

    @Transactional
    public Order create(Order order){
        return orderRepo.save(order);
    }

    @Transactional
    public Order changeStatus(Long id, String statusName) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(statusName));
        return orderRepo.save(order);
    }


    @Transactional
    public Order update(Long id, Order order) {
        order.setId(id);
        return orderRepo.save(order);
    }


    public Page<Order> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Transactional
    public void remove(Long id) {
        orderRepo.deleteById(id);
    }


    public Long getIncome(LocalTime timeFrom,
                          LocalTime timeUntil,
                          LocalDate dateFrom,
                          LocalDate dateUntil) {
        return incomeSpecificationFactory.getIncome(timeFrom, timeUntil, dateFrom, dateUntil);
    }


    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
    }


    public Page<Order> getOrdersByParameter(Box box,
                                            LocalTime timeFrom,
                                            LocalTime timeUntil,
                                            LocalDate dateFrom,
                                            LocalDate dateUntil,
                                            Pageable pageable) {
        return orderRepo.findAll(orderSpecificationFactory
                .getSpecificationForOrders(box, timeFrom, timeUntil, dateFrom, dateUntil), pageable);
    }
}
