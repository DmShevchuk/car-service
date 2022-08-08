package com.example.carservice.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveDTO {
    @NotNull(message = "Date of appointment not specified!")
    private LocalDate date;

    @NotNull(message = "Time of appointment not specified!")
    private LocalTime time;

    @NotBlank(message = "Service type name not specified!")
    private String serviceTypeName;

    @NotNull(message = "User id not specified!")
    @Min(value = 0, message = "User id can't be less than 0!")
    private Long userId;
}
