package com.example.carservice.exceptions;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class UnableToFindFreeBoxException extends RuntimeException{
    public UnableToFindFreeBoxException(LocalTime time, LocalDate date){
        super(String.format("Unable to find free box for %s at %s!", date, time));
    }
}
