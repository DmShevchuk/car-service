package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Order;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO add(@Valid @RequestBody OrderSaveDTO orderSaveDTO){
        Order order = modelMapper.map(orderSaveDTO, Order.class);
        return OrderDTO.toDTO(orderService.create(order));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getAll(@PageableDefault Pageable pageable){
        Page<Order> orders = orderService.getAll(pageable);
        return orders.map(o -> modelMapper.map(o, OrderDTO.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long id){
        return OrderDTO.toDTO(orderService.getOrderById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO update(@PathVariable Long id,
                         @Valid @RequestBody OrderSaveDTO orderSaveDTO){
        Order order = modelMapper.map(orderSaveDTO, Order.class);
        return OrderDTO.toDTO(orderService.update(id, order));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id){
        orderService.remove(id);
        return String.format("Order with id=%d was deleted successfully!", id);
    }
}
