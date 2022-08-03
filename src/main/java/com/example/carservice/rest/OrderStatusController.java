package com.example.carservice.rest;

import com.example.carservice.dto.orderStatus.OrderStatusDTO;
import com.example.carservice.dto.orderStatus.OrderStatusSaveDTO;
import com.example.carservice.entities.OrderStatus;
import com.example.carservice.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderStatusDTO add(@Valid @RequestBody OrderStatusSaveDTO orderStatusSaveDTO){
        OrderStatus orderStatus = modelMapper.map(orderStatusSaveDTO, OrderStatus.class);
        return OrderStatusDTO.toDTO(orderStatusService.add(orderStatus));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderStatusDTO> getAll(@PageableDefault Pageable pageable){
        Page<OrderStatus> orderStatuses = orderStatusService.getAll(pageable);
        return orderStatuses.map(s -> modelMapper.map(s, OrderStatusDTO.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusDTO getOrderStatusById(@PathVariable Long id){
        return OrderStatusDTO.toDTO(orderStatusService.getOrderStatusById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusDTO update(@PathVariable Long id,
                                 @Valid @RequestBody OrderStatusSaveDTO orderStatusSaveDTO){
        OrderStatus orderStatus = modelMapper.map(orderStatusSaveDTO, OrderStatus.class);
        return OrderStatusDTO.toDTO(orderStatusService.update(id, orderStatus));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id){
        orderStatusService.remove(id);
        return String.format("Order status with id=%d was deleted successfully!", id);
    }
}
