package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderService;
import com.example.carservice.utils.DateValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/stats-info")
@RequiredArgsConstructor
public class CommonController {
    private final OrderService orderService;
    private final BoxService boxService;
    private final ModelMapper modelMapper;


    @GetMapping("/income")
    @ResponseStatus(HttpStatus.OK)
    public String getIncome(@RequestParam(required = false) LocalTime timeFrom,
                            @RequestParam(required = false) LocalTime timeUntil,
                            @RequestParam(required = false) String dateFrom,
                            @RequestParam(required = false) String dateUntil) throws ParseException {
        Date dateStart = null;
        if (dateFrom != null) {
            dateStart = DateValidator.parseDate(dateFrom);
        }

        Date dateFinish = null;
        if (dateUntil != null) {
            dateFinish = DateValidator.parseDate(dateUntil);
        }

        Long income = orderService.getIncome(
                timeFrom,
                timeUntil,
                dateStart,
                dateFinish
        );
        return String.format("Доход за указанный период: %d рублей.", income);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getOrders(@RequestParam(required = false) Long boxId,
                                 @RequestParam(required = false) LocalTime timeFrom,
                                 @RequestParam(required = false) LocalTime timeUntil,
                                 @RequestParam(required = false) String dateFrom,
                                 @RequestParam(required = false) String dateUntil,
                                 @PageableDefault Pageable pageable) throws ParseException {

        Box box = null;
        if (boxId != null) {
            box = boxService.getBoxById(boxId);
        }

        Date dateStart = null;
        if (dateFrom != null) {
            dateStart = DateValidator.parseDate(dateFrom);
        }

        Date dateFinish = null;
        if (dateUntil != null) {
            dateFinish = DateValidator.parseDate(dateUntil);
        }

        Page<Order> orders = orderService.getOrdersByParameter(
                box,
                timeFrom,
                timeUntil,
                dateStart,
                dateFinish,
                pageable
        );
        return orders.map(o -> modelMapper.map(o, OrderDTO.class));
    }
}
