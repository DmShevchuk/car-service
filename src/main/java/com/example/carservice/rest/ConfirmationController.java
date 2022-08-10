package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.services.ConfirmationService;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/confirmation")
@RequiredArgsConstructor
public class ConfirmationController {
    private final OrderService orderService;
    private final ConfirmationService confirmationService;

    @GetMapping("/{token}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO confirmToken(@PathVariable String token){
        Long orderId = confirmationService.confirmOrder(token);
        Order confirmedOrder = orderService.changeStatus(orderId, OrderStatusEnum.CONFIRMED.toString());
        return OrderDTO.toDTO(confirmedOrder);
    }
}
