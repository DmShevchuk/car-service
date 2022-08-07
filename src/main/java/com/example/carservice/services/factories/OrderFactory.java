package com.example.carservice.services.factories;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderFactory {
    private final BoxService boxService;
    private final UserService userService;
    private final ServiceTypeService serviceTypeService;
    private final OrderStatusService orderStatusService;


    public Order buildOrder(OrderSaveDTO orderDTO) throws ParseException {
        Order order = new Order();

        LocalTime time = orderDTO.getTime();
        Date date = orderDTO.getDate();
        ServiceType serviceType = serviceTypeService.getServiceTypeByName(orderDTO.getServiceTypeName());
        Box box = boxService.getBestBoxForOrder(time, date, serviceType.getDuration());

        order.setTimeStart(time);
        order.setTimeEnd(countEndTime(time, serviceType.getDuration(), box.getTimeFactor()));
        order.setDate(date);
        order.setUser(userService.getUserById(orderDTO.getUserId()));
        order.setBox(box);
        order.setServiceType(serviceType);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.AWAITING_CONFIRMATION.toString()));
        return order;
    }

    private LocalTime countEndTime(LocalTime startTime, LocalTime basicDuration, Float boxTimeFactor) {
        int minutesInHour = 60;

        int duration = Math.round(
                (basicDuration.getHour() * minutesInHour + basicDuration.getMinute()) / boxTimeFactor);
        return startTime
                .plusHours(duration / minutesInHour)
                .plusMinutes(duration % minutesInHour);
    }
}
