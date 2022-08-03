package com.example.carservice.dto.orderStatus;

import com.example.carservice.entities.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusDTO {
    private Long id;
    private String statusName;

    public static OrderStatusDTO toDTO(OrderStatus orderStatus){
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(orderStatus.getId());
        orderStatusDTO.setStatusName(orderStatus.getStatusName());
        return orderStatusDTO;
    }
}
