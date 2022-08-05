package com.example.carservice.dto.serviceType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@RequiredArgsConstructor
public class ServiceTypeSaveDTO {
    @NotBlank(message = "Service name not specified!")
    private String serviceName;

    @NotNull(message = "Service duration not specified!")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Time does not match the pattern 'HH:mm'!")
    private String duration;

    @NotNull(message = "Service price not specified!")
    @Min(value = 0, message = "Price can't be less than 0!")
    private Long price;
}
