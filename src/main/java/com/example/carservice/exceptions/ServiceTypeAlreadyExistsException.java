package com.example.carservice.exceptions;

public class ServiceTypeAlreadyExistsException extends RuntimeException {
    public ServiceTypeAlreadyExistsException(String name) {
        super(String.format("Service type with name '%s' already exists!", name));
    }
}