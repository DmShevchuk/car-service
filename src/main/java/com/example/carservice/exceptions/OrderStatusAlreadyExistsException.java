package com.example.carservice.exceptions;

public class OrderStatusAlreadyExistsException extends RuntimeException {
    public OrderStatusAlreadyExistsException(String name) {
        super(String.format("Order status with name '%s' already exists!", name));
    }
}