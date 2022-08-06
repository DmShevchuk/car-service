package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.services.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/confirmation")
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @GetMapping("/{token}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO confirmToken(@PathVariable String token){
        return OrderDTO.toDTO(confirmationService.confirmOrder(token));
    }
}
