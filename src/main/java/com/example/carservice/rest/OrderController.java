package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Confirmation;
import com.example.carservice.entities.Order;
import com.example.carservice.services.ConfirmationService;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final ConfirmationService confirmationService;

    @Value("${time.defaultZoneId}")
    private String defaultZoneId;

    @Value("${time.confirmationPeriodInMinutes}")
    private Long confirmationPeriodInMinutes;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO add(@Valid @RequestBody OrderSaveDTO orderSaveDTO) throws ParseException {

        Order createdOrder = orderService.create(orderSaveDTO);
        Confirmation confirmation = new Confirmation();
        System.out.println("expire at " + LocalDateTime.now(ZoneId.of(defaultZoneId)).plusMinutes(confirmationPeriodInMinutes));
        confirmation.setExpireAt(LocalDateTime.now(ZoneId.of(defaultZoneId)).plusMinutes(confirmationPeriodInMinutes));

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;

        String generatedToken = RandomStringUtils.random(length, useLetters, useNumbers);

        confirmation.setToken(generatedToken);


        confirmation.setOrder(createdOrder);
        confirmationService.createConfirmation(confirmation);
        System.out.println("localhost:8080/api/v1/confirmation/" + generatedToken);


        return OrderDTO.toDTO(createdOrder);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getAll(@PageableDefault Pageable pageable) {
        Page<Order> orders = orderService.getAll(pageable);
        return orders.map(o -> modelMapper.map(o, OrderDTO.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long id) {
        return OrderDTO.toDTO(orderService.getOrderById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO update(@PathVariable Long id,
                           @Valid @RequestBody OrderSaveDTO orderSaveDTO) {
        Order order = modelMapper.map(orderSaveDTO, Order.class);
        return OrderDTO.toDTO(orderService.update(id, order));
    }

    @PatchMapping("/{id}/statuses")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO changeStatus(@PathVariable Long id,
                           @RequestParam String newStatus) {
        return OrderDTO.toDTO(orderService.changeStatus(id, newStatus));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        orderService.remove(id);
        return String.format("Order with id=%d was deleted successfully!", id);
    }
}
