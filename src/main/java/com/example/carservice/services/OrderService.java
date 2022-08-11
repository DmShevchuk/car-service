package com.example.carservice.services;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.OrderStatus;
import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.services.factories.ConfirmationFactory;
import com.example.carservice.services.factories.OrderFactory;
import com.example.carservice.specification.IncomeSpecificationFactory;
import com.example.carservice.specification.impl.CommonSpecificationBuilder;
import com.example.carservice.specification.OrderSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;


/**
 * Сервис для работы с заказами
 * */
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

    @Value("${time.defaultZoneId}")
    private String zoneId;


    /**
     * Создание нового закза
     * */
    @Transactional
    public Order create(Order order) {
        return orderRepo.save(order);
    }



    /**
     * Изменение статуса заказа
     * */
    @Transactional
    public Order changeStatus(Long id, String statusName) {
        if (!"CANCELED".equals(statusName) && !"FINISHED".equals(statusName)){
            throw new RuntimeException("Unable to get change status!");
        }
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(statusName));
        return orderRepo.save(order);
    }



    /**
     * Обновление заказа
     * */
    @Transactional
    public Order update(Long id, OrderSaveDTO orderSaveDTO) {
        Order order = getOrderById(id);
        String previousStatusName = order.getOrderStatus().getStatusName();
        remove(id);
        try {
            Order newOrder = orderFactory.buildOrder(orderSaveDTO);
            confirmationFactory.createConfirmation(newOrder);
            newOrder.setId(id);
            return orderRepo.save(newOrder);
        }catch (Exception e){
            changeStatus(id, previousStatusName);
            return order;
        }
    }


    /**
     * Изменение статуса заказа на CLIENT_CHECKED_IN
     * */
    @Transactional
    public Order clientCheckIn(Long id) {
        Order order = getOrderById(id);
        if (!"CONFIRMED".equals(order.getOrderStatus().getStatusName())){
            throw new RuntimeException("Something is wrong with the order: it may have been canceled or finished!");
        }

        LocalDateTime orderStartTimestamp = LocalDateTime.of(order.getDateStart(), order.getTimeStart());
        LocalDateTime clientCheckTimestamp = LocalDateTime.now(ZoneId.of(zoneId));
        if (orderStartTimestamp.isBefore(clientCheckTimestamp)){
            throw new RuntimeException("You late! Order cancelled!");
        }
        if(clientCheckTimestamp.isBefore(orderStartTimestamp.minusMinutes(30))){
            throw new RuntimeException("It's impossible to check in so early!");
        }

        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.CLIENT_CHECKED_IN.toString()));
        return orderRepo.save(order);
    }



    /**
     * Получение всех заказов
     * */
    public Page<Order> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }



    /**
     * Мягкое удаление заказа
     * */
    @Transactional
    public void remove(Long id) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.CANCELED.toString()));
        orderRepo.save(order);
    }


    /**
     * Получение информации о доходе за указанный период
     * */
    public Long getIncome(LocalTime timeFrom,
                          LocalTime timeUntil,
                          LocalDate dateFrom,
                          LocalDate dateUntil) {
        return incomeSpecificationFactory.getIncome(timeFrom, timeUntil, dateFrom, dateUntil);
    }


    /**
     * Получение заказа по id
     * */
    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
    }


    /**
     * Получение заказа по и/или боксу и/или дате и/или времени
     * */
    public Page<Order> getOrdersByParameter(Box box,
                                            LocalTime timeFrom,
                                            LocalTime timeUntil,
                                            LocalDate dateFrom,
                                            LocalDate dateUntil,
                                            Pageable pageable) {
        return orderRepo.findAll(orderSpecificationFactory
                .getSpecificationForOrders(box, timeFrom, timeUntil, dateFrom, dateUntil), pageable);
    }


    /**
     * Получение всех заказов пользователя статусу
     * */
    public Page<Order> getAllByUserAndStatus(User user, OrderStatusEnum orderStatus, Pageable pageable) {
        OrderStatus orderSt = orderStatusService.getOrderStatusByName(orderStatus.toString());
        return orderRepo.findAll(specificationBuilder.getUserOrders(user, orderSt), pageable);
    }


    /**
     * Подтверждение заказа
     * */
    @Transactional
    public Order confirmOrder(Long id) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.CONFIRMED.toString()));
        return orderRepo.save(order);
    }

}
