package com.example.carservice.dto.serviceType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ServiceTypeSaveDTO {
    @NotBlank(message = "Service name not specified!")
    private String serviceName;

    @NotNull(message = "Service duration not specified!")
    private LocalTime duration;

    @NotNull(message = "Service price not specified!")
    @Min(value = 0, message = "Price can't be less than 0!")
    private Long price;
}
