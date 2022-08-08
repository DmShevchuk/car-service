package com.example.carservice.exceptions;

public class ServiceTypeNotFoundException extends RuntimeException{
    public ServiceTypeNotFoundException(String name) {
        super(String.format("Service type with name '%s' does not exist!", name));
    }
}