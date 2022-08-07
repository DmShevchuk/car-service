package com.example.carservice.services;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.specification.impl.IncomeSpecificationFactoryImpl;
import com.example.carservice.specification.impl.OrderSpecificationFactoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final BoxService boxService;
    private final ServiceTypeService serviceTypeService;
    private final UserService userService;
    private final OrderStatusService orderStatusService;
    private final IncomeSpecificationFactoryImpl incomeSpecificationFactory;
    private final OrderSpecificationFactoryImpl orderSpecificationFactory;

    public Order create(OrderSaveDTO orderDTO) throws ParseException {
        Order order = new Order();

        LocalTime time = orderDTO.getTime();
        Date date = orderDTO.getDate();
        ServiceType serviceType =  serviceTypeService.getServiceTypeByName(orderDTO.getServiceTypeName());
        Box box = boxService.getBestBoxForOrder(time, date, serviceType.getDuration());

        LocalTime serviceDuration = serviceType.getDuration();
        order.setTimeStart(time);
        int duration =Math.round((serviceDuration.getHour() * 60 + serviceDuration.getMinute()) / box.getTimeFactor());
        int hours = duration / 60;
        int minutes = duration % 60;
        System.out.println(hours);
        System.out.println(minutes);
        order.setTimeStart(time);
        LocalTime endTime = time.plusHours(hours).plusMinutes(minutes);

        order.setTimeEnd(endTime);
        order.setDate(date);
        order.setUser(userService.getUserById(orderDTO.getUserId()));
        order.setBox(box);
        order.setServiceType(serviceType);
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
