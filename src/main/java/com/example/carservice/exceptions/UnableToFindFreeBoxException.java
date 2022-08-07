package com.example.carservice.exceptions;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class UnableToFindFreeBoxException extends RuntimeException{
    private final LocalTime time;
    private final Date date;

    public UnableToFindFreeBoxException(LocalTime time, Date date){
        this.time = time;
        this.date = date;
    }

    public String getMessage(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Unable to find free box for %s at %s!", dateFormat.format(date), time);
    }
}
