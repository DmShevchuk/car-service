package com.example.carservice.dto.box;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.example.carservice.entities.Box;


/**
 * Класс dto для объектов класса {@link Box}
 * */
@Getter
@Setter
@NoArgsConstructor
public class BoxSaveDTO {
    /**
     * Уникальное название бокса
     * */
    @NotBlank(message = "Box name not specified!")
    private String name;


    /**
     * Время начала работы в формате HH:mm
     * */
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Time does not match the pattern 'HH:mm'!")
    private String startWorkTime;


    /**
     * Время окончания работы в формате HH:mm
     * */
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Time does not match the pattern 'HH:mm'!")
    private String endWorkTime;


    /**
     * Коэффициент производительности. <br/>
     *
     * Например, если время исполнения услуги - 30 минут, то в боксе с timeFactor = 2.0 <br/>
     * Она будет выполнена за 15 минут.
     * */
    @NotNull(message = "Time factor not specified!")
    private Float timeFactor;


    /**
     * true, если бокс работает 24 часа
     * */
    private Boolean twentyFourHour = false;
}
