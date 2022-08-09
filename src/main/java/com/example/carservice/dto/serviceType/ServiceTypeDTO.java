package com.example.carservice.dto.serviceType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ServiceTypeDTO {
    private Long id;
    private String serviceName;
    private LocalTime duration;
    private Long price;
}
