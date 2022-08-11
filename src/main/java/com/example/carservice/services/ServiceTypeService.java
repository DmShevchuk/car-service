package com.example.carservice.services;

import com.example.carservice.entities.ServiceType;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.exceptions.data.IncorrectServiceTypeDuration;
import com.example.carservice.exceptions.entities.ServiceTypeAlreadyExistsException;
import com.example.carservice.exceptions.entities.ServiceTypeNotFoundException;
import com.example.carservice.repos.ServiceTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;


/**
 * Класс для работы с объектами {@link ServiceType}
 * */
@Service
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepo serviceTypeRepo;


    /**
     * Добавление в систему нового типа услуги <br/>
     * Происходит проверка на уникальность названия <br/>
     * И проверка на корректность длительности услуги
     * */
    @Transactional
    public ServiceType add(ServiceType serviceType) {
        if (serviceTypeRepo.existsByServiceName(serviceType.getServiceName())) {
            throw new ServiceTypeAlreadyExistsException(serviceType.getServiceName());
        }
        if (serviceType.getDuration().equals(LocalTime.of(0, 0))){
            throw new IncorrectServiceTypeDuration();
        }
        return serviceTypeRepo.save(serviceType);
    }


    /**
     * Получение списка всех услуг
     * */
    public Page<ServiceType> getAll(Pageable pageable) {
        return serviceTypeRepo.findAll(pageable);
    }


    /**
     * Получение услуги по id
     * */
    public ServiceType getServiceTypeById(Long id) {
        return serviceTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType", id));
    }


    /**
     * Получение услуги по имени
     * */
    public ServiceType getServiceTypeByName(String name) {
        return serviceTypeRepo.findServiceTypeByServiceName(name)
                .orElseThrow(() -> new ServiceTypeNotFoundException(name));
    }
}
