package com.example.carservice.dto.order;

import com.example.carservice.dto.serviceType.ServiceTypeDTO;
import com.example.carservice.entities.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDate dateStart;
    private LocalTime timeStart;
    private LocalDate dateEnd;
    private LocalTime timeEnd;
    private Long userId;
    private Long boxId;
    private ServiceTypeDTO serviceTypeDTO;
    private String orderStatus;

    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDateStart(order.getDateStart());
        orderDTO.setTimeStart(order.getTimeStart());
        orderDTO.setDateEnd(order.getDateEnd());
        orderDTO.setTimeEnd(order.getTimeEnd());
        if (order.getUser() != null) {
            orderDTO.setUserId(order.getUser().getId());
        }
        if (order.getBox() != null) {
            orderDTO.setBoxId(order.getBox().getId());
        }
        if (order.getServiceType() != null) {
            orderDTO.setServiceTypeDTO(ServiceTypeDTO.toDTO(order.getServiceType()));
        }
        if (order.getOrderStatus() != null) {
            orderDTO.setOrderStatus(order.getOrderStatus().getStatusName());
        }
        return orderDTO;
    }
}
