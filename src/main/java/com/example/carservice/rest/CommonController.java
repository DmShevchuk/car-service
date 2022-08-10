package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.security.AccessValidator;
import com.example.carservice.security.Role;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderService;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/stats-info")
@RequiredArgsConstructor
public class CommonController {
    private final UserService userService;
    private final OrderService orderService;
    private final BoxService boxService;
    private final ModelMapper modelMapper;
    private final AccessValidator accessValidator;

    @GetMapping("/income")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public String getIncome(@RequestParam(required = false) LocalTime timeFrom,
                            @RequestParam(required = false) LocalTime timeUntil,
                            @RequestParam(required = false) LocalDate dateFrom,
                            @RequestParam(required = false) LocalDate dateUntil) {
        Long income = orderService.getIncome(
                timeFrom,
                timeUntil,
                dateFrom,
                dateUntil
        );
        return String.format("Доход за указанный период: %d рублей.", income);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || hasRole('OPERATOR')")
    public Page<OrderDTO> getOrders(@RequestParam(required = false) Long boxId,
                                    @RequestParam(required = false) LocalTime timeFrom,
                                    @RequestParam(required = false) LocalTime timeUntil,
                                    @RequestParam(required = false) LocalDate dateFrom,
                                    @RequestParam(required = false) LocalDate dateUntil,
                                    @PageableDefault Pageable pageable) {

        if (!accessValidator.operatorHasAccess(boxId)){
            throw new RuntimeException("Operator has no access to specified box!");
        }

        Box box = null;
        if (boxId != null) {
            box = boxService.getBoxById(boxId);
        }
        Page<Order> orders = orderService.getOrdersByParameter(
                box,
                timeFrom,
                timeUntil,
                dateFrom,
                dateUntil,
                pageable
        );
        return orders.map(o -> modelMapper.map(o, OrderDTO.class));
    }

}
