package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.dto.order.OrderPatchDTO;
import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Order;
import com.example.carservice.services.OrderService;
import com.example.carservice.services.factories.ConfirmationFactory;
import com.example.carservice.services.factories.OrderFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;
    private final ConfirmationFactory confirmationFactory;
    private final OrderFactory orderFactory;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO add(@Valid @RequestBody OrderSaveDTO orderSaveDTO) throws ParseException {
        Order order = orderService.create(
                orderFactory.buildOrder(orderSaveDTO)
        );
        confirmationFactory.createConfirmation(order);
        return OrderDTO.toDTO(orderService.create(order));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getAll(@PageableDefault Pageable pageable) {
        Page<Order> orders = orderService.getAll(pageable);
        return orders.map(OrderDTO::toDTO);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long id) {
        return OrderDTO.toDTO(orderService.getOrderById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || @accessValidator.canChangeOrder(principal, #id)")
    public OrderDTO update(@PathVariable Long id,
                           @Valid @RequestBody OrderSaveDTO orderSaveDTO) {
        return OrderDTO.toDTO(orderService.update(id, orderSaveDTO));
    }

    @PatchMapping("/{id}/check-in")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || @accessValidator.canChangeOrder(principal, #id)")
    public OrderDTO clientCheckIn(@PathVariable Long id) {
        return OrderDTO.toDTO(orderService.clientCheckIn(id));
    }


    @PatchMapping("/{id}/statuses")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || @accessValidator.canChangeOrder(principal, #id)")
    public OrderDTO changeStatus(@PathVariable Long id,
                                 @Valid @RequestBody OrderPatchDTO orderPatchDTO) {
        String newStatus = orderPatchDTO.getOrderStatus().toString();
        return OrderDTO.toDTO(orderService.changeStatus(id, newStatus));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        orderService.remove(id);
        return String.format("Order with id=%d was deleted successfully!", id);
    }
}
