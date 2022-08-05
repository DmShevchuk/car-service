package com.example.carservice.utils;

import com.example.carservice.dto.serviceType.ServiceTypeSaveDTO;
import com.example.carservice.entities.ServiceType;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
public class ServiceTypeSaveDtoValidator {
    public ServiceType validate(ServiceTypeSaveDTO serviceTypeDTO){
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceName(serviceTypeDTO.getServiceName());
        serviceType.setDuration(validateTime(serviceTypeDTO.getDuration()));
        serviceType.setPrice(serviceTypeDTO.getPrice());
        return serviceType;
    }

    private Time validateTime(String value) {
        String seconds = ":00";
        return Time.valueOf(value + seconds);
    }
}
