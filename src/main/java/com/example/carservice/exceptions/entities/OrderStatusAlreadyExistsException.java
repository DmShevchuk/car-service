package com.example.carservice.exceptions.entities;

public class OrderStatusAlreadyExistsException extends RuntimeException {
    public OrderStatusAlreadyExistsException(String name) {
        super(String.format("Order status with name '%s' already exists!", name));
    }
}