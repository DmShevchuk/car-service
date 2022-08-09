package com.example.carservice.services;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.services.factories.ConfirmationFactory;
import com.example.carservice.services.factories.OrderFactory;
import com.example.carservice.specification.IncomeSpecificationFactory;
import com.example.carservice.specification.impl.CommonSpecificationBuilder;
import com.example.carservice.specification.OrderSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderFactory orderFactory;
    private final ConfirmationFactory confirmationFactory;
    private final OrderStatusService orderStatusService;
    private final IncomeSpecificationFactory incomeSpecificationFactory;
    private final OrderSpecificationFactory orderSpecificationFactory;
    private final CommonSpecificationBuilder specificationBuilder;

    @Transactional
    public Order create(Order order) {
        return orderRepo.save(order);
    }

    @Transactional
    public Order changeStatus(Long id, String statusName) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(statusName));
        return orderRepo.save(order);
    }

    @Transactional
    public Order update(Long id, OrderSaveDTO orderSaveDTO) {
        Order order = getOrderById(id);
        String previousStatusName = order.getOrderStatus().getStatusName();
        remove(id);
        try {
            Order newOrder = create(orderFactory.buildOrder(orderSaveDTO));
            confirmationFactory.createConfirmation(newOrder);
            newOrder.setId(id);
            return orderRepo.save(newOrder);
        }catch (Exception e){
            changeStatus(id, previousStatusName);
            return order;
        }
    }


    public Page<Order> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Transactional
    public void remove(Long id) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.CANCELED.toString()));
        orderRepo.save(order);
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

    public Page<Order> getAllByUserAndStatus(User user, OrderStatusEnum orderStatus, Pageable pageable) {
        return orderRepo.findAll(specificationBuilder.getUserOrders(user, orderStatus), pageable);
    }
}
