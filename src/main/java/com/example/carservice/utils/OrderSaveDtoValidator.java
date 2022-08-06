package com.example.carservice.utils;

import com.example.carservice.dto.order.OrderSaveDTO;
import com.example.carservice.entities.Order;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class OrderSaveDtoValidator {
    public LocalTime parseOrderTime(String value){
        return LocalTime.parse(value);
    }
}
