package com.example.carservice.services;

import com.example.carservice.entities.ServiceType;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.exceptions.ServiceTypeAlreadyExistsException;
import com.example.carservice.exceptions.ServiceTypeNotFoundException;
import com.example.carservice.repos.ServiceTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {
    private final ServiceTypeRepo serviceTypeRepo;

    public ServiceType add(ServiceType serviceType) {
        if (serviceTypeRepo.existsByServiceName(serviceType.getServiceName())) {
            throw new ServiceTypeAlreadyExistsException(serviceType.getServiceName());
        }
        return serviceTypeRepo.save(serviceType);
    }

    public ServiceType update(Long id, ServiceType serviceType) {
        serviceType.setId(id);
        return serviceTypeRepo.save(serviceType);
    }

    public Page<ServiceType> getAll(Pageable pageable) {
        return serviceTypeRepo.findAll(pageable);
    }

    public void remove(Long id) {
        serviceTypeRepo.deleteById(id);
    }

    public ServiceType getServiceTypeById(Long id) {
        return serviceTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType", id));
    }

    public ServiceType getServiceTypeByName(String name) {
        return serviceTypeRepo.findServiceTypeByServiceName(name)
                .orElseThrow(() -> new ServiceTypeNotFoundException(name));
    }
}
