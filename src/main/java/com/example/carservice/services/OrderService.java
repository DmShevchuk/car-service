package com.example.carservice.services;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.specification.impl.IncomeSpecificationFactoryImpl;
import com.example.carservice.specification.impl.OrderSpecificationFactoryImpl;
import com.example.carservice.utils.OrderSaveDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderSaveDtoValidator orderValidator;
    private final BoxService boxService;
    private final ServiceTypeService serviceTypeService;
    private final UserService userService;
    private final OrderStatusService orderStatusService;
    private final IncomeSpecificationFactoryImpl incomeSpecificationFactory;
    private final OrderSpecificationFactoryImpl orderSpecificationFactory;

    public Order create(OrderSaveDTO orderDTO) {
        Order order = new Order();
        order.setTime(orderValidator.parseOrderTime(orderDTO.getTime()));
        order.setDate(orderDTO.getDate());
        order.setUser(userService.getUserById(orderDTO.getUserId()));
        order.setBox(boxService.getBoxById(1L));
        order.setServiceType(serviceTypeService.getServiceTypeByName(orderDTO.getServiceTypeName()));
        order.setOrderStatus(orderStatusService.getOrderStatusById(1L));
        return orderRepo.save(order);
    }

    public Order changeStatus(Long id, String statusName) {
        Order order = getOrderById(id);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(statusName));
        return orderRepo.save(order);
    }

    public Order update(Long id, Order order) {
        order.setId(id);
        return orderRepo.save(order);
    }

    public Page<Order> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    public void remove(Long id) {
        orderRepo.deleteById(id);
    }

    public Long getIncome(LocalTime timeFrom,
                          LocalTime timeUntil,
                          Date dateFrom,
                          Date dateUntil) {
        return incomeSpecificationFactory.getIncome(timeFrom, timeUntil, dateFrom, dateUntil);
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
    }

    public Page<Order> getOrdersByParameter(Box box,
                                            LocalTime timeFrom,
                                            LocalTime timeUntil,
                                            Date dateFrom,
                                            Date dateUntil,
                                            Pageable pageable) {
        return orderRepo.findAll(orderSpecificationFactory
                        .getSpecificationForOrders(box, timeFrom, timeUntil, dateFrom, dateUntil), pageable);
    }
}
