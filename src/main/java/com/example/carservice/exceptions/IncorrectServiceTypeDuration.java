package com.example.carservice.exceptions;

public class IncorrectServiceTypeDuration extends RuntimeException{
    public IncorrectServiceTypeDuration(){
        super("The service cannot have a duration of 0 minutes!");
    }
}
