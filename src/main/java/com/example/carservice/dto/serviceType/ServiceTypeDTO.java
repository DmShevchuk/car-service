package com.example.carservice.dto.serviceType;

import com.example.carservice.entities.ServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class ServiceTypeDTO {
    private Long id;
    private String serviceName;
    private Time duration;
    private Long price;

    public static ServiceTypeDTO toDTO(ServiceType serviceType){
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(serviceType.getId());
        serviceTypeDTO.setServiceName(serviceType.getServiceName());
        serviceTypeDTO.setDuration(serviceType.getDuration());
        serviceTypeDTO.setPrice(serviceType.getPrice());
        return serviceTypeDTO;
    }
}
