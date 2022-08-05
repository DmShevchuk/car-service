package com.example.carservice.utils;

import com.example.carservice.dto.box.BoxSaveDTO;
import com.example.carservice.entities.Box;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
public class BoxSaveDtoValidator {
    public Box validate(BoxSaveDTO boxDTO) {
        Box box = new Box();
        if (!boxDTO.getTwentyFourHour()) {
            Time startTime = validateTime(boxDTO.getStartWorkTime());
            Time endTime = validateTime(boxDTO.getEndWorkTime());
            box.setStartWorkTime(startTime);
            box.setEndWorkTime(endTime);
        }

        box.setName(boxDTO.getName());
        box.setTimeFactor(boxDTO.getTimeFactor());
        box.setTwentyFourHour(boxDTO.getTwentyFourHour());
        return box;
    }

    private Time validateTime(String value) {
        String seconds = ":00";
        return Time.valueOf(value + seconds);
    }
}
