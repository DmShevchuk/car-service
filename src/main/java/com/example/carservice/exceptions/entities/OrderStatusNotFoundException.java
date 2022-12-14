package com.example.carservice.exceptions.entities;

public class OrderStatusNotFoundException extends RuntimeException{
    public OrderStatusNotFoundException(String name) {
        super(String.format("Order status with name '%s' does not exist!", name));
    }
}