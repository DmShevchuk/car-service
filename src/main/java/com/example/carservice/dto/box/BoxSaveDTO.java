package com.example.carservice.dto.box;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.example.carservice.entities.Box;

import java.time.LocalTime;


/**
 * Класс dto для объектов класса {@link Box}
 * */
@Getter
@Setter
@NoArgsConstructor
public class BoxSaveDTO {
    @NotBlank(message = "Box name not specified!")
    private String name;

    private LocalTime startWorkTime;

    private LocalTime endWorkTime;

    @NotNull(message = "Time factor not specified!")
    private Float timeFactor;

    private Boolean twentyFourHour = false;
}
