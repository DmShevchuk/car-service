package com.example.carservice.dto.box;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class BoxSaveDTO {
    @NotBlank(message = "Box name not specified!")
    private String name;

    @NotNull(message = "Start work time not specified!")
    @DateTimeFormat(pattern = "HH:mm:SS")
    private Time startWorkTime;

    @NotNull(message = "End work time not specified!")
    @DateTimeFormat(pattern = "HH:mm:SS")
    private Time endWorkTime;

    @NotNull(message = "Time factor not specified!")
    private Float timeFactor;
}
