package com.example.carservice.exceptions;

public class IncorrectDiscountValueException extends RuntimeException {
    public IncorrectDiscountValueException() {
        super("Minimum discount must be less than maximum discount!");
    }
}