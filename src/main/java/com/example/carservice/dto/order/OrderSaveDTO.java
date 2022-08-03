package com.example.carservice.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveDTO {
    @NotNull(message = "Date and time of appointment not specified!")
    private Date appointmentDate;

    @NotBlank(message = "Service type name not specified!")
    private String serviceTypeName;

    @NotNull(message = "User id not specified!")
    @Min(value = 0, message = "User id can't be less than 0!")
    private Long userId;
}
