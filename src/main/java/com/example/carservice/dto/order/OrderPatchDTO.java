package com.example.carservice.dto.order;

import com.example.carservice.entities.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderPatchDTO {
    @NotNull(message = "New order status should be specified!")
    private OrderStatusEnum orderStatus;
}
