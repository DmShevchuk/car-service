package com.example.carservice.services.factories;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderStatusService;
import com.example.carservice.services.ServiceTypeService;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderFactory {
    private final BoxService boxService;
    private final UserService userService;
    private final ServiceTypeService serviceTypeService;
    private final OrderStatusService orderStatusService;
    @Value("${time.defaultZoneId}")
    private String zoneId;

    public Order buildOrder(OrderSaveDTO orderDTO) throws ParseException {
        Order order = new Order();

        LocalTime time = orderDTO.getTime();
        Date date = orderDTO.getDate();
        ServiceType serviceType = serviceTypeService.getServiceTypeByName(orderDTO.getServiceTypeName());
        Box box = boxService.getBestBoxForOrder(time, date, serviceType.getDuration());
        LocalDateTime endDateTime = countEndDateTime(time, date, serviceType.getDuration(), box.getTimeFactor());

        order.setTimeStart(time);
        order.setDateStart(date);
        order.setTimeEnd(endDateTime.toLocalTime());
        order.setDateEnd(Date.from(endDateTime.toLocalDate().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()));
        order.setUser(userService.getUserById(orderDTO.getUserId()));
        order.setBox(box);
        order.setServiceType(serviceType);
        order.setOrderStatus(orderStatusService.getOrderStatusByName(OrderStatusEnum.AWAITING_CONFIRMATION.toString()));
        return order;
    }

    private LocalDateTime countEndDateTime(LocalTime startTime, Date startDate,
                                           LocalTime basicDuration, Float boxTimeFactor) {
        int minutesInHour = 60;
        LocalDateTime localDateTime = LocalDateTime.of(startDate.toInstant()
                .atZone(ZoneId.of(zoneId))
                .toLocalDate(), startTime);
        int duration = Math.round(
                (basicDuration.getHour() * minutesInHour + basicDuration.getMinute()) / boxTimeFactor);
        return localDateTime
                .plusHours(duration / minutesInHour)
                .plusMinutes(duration % minutesInHour);
    }
}
