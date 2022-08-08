package com.example.carservice.exceptions;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class UnableToFindFreeBoxException extends RuntimeException{
    private final LocalTime time;
    private final LocalDate date;

    public UnableToFindFreeBoxException(LocalTime time, LocalDate date){
        this.time = time;
        this.date = date;
    }

    public String getMessage(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Unable to find free box for %s at %s!", dateFormat.format(date), time);
    }
}
