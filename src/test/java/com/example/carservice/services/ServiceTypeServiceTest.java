package com.example.carservice.services;

import com.example.carservice.entities.ServiceType;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.ServiceTypeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ServiceTypeServiceTest {
    Set<Long> setServiceTypeIds;
    Pageable pageable;
    Page<ServiceType> page;

    @Mock
    private ServiceTypeRepo serviceTypeRepo;
    private final ServiceTypeService serviceTypeService;
    private final ServiceType serviceType;
    private final Long id = 1L;

    public ServiceTypeServiceTest() {
        MockitoAnnotations.openMocks(this);
        String serviceName = "Service name 1";
        Long servicePrice = 1000L;

        serviceTypeService = new ServiceTypeService(serviceTypeRepo);
        serviceType = new ServiceType();
        serviceType.setId(id);
        serviceType.setServiceName(serviceName);
        serviceType.setDuration(LocalTime.now());
        serviceType.setPrice(servicePrice);

        setServiceTypeIds = Set.of(serviceType.getId());
        pageable = PageRequest.of(0, 5);
        page = new PageImpl<>(List.of(serviceType));
    }


    @Test
    @DisplayName("Create service with correct data")
    void save_WithCorrectData() {
        Mockito
                .doReturn(serviceType)
                .when(serviceTypeRepo)
                .save(serviceType);
        Long serviceId = serviceTypeService.add(serviceType).getId();
        Mockito.verify(serviceTypeRepo, Mockito.times(1)).save(serviceType);
        Assertions.assertEquals(serviceType.getId(), serviceId);
    }


    @Test
    @DisplayName("Find service types with correct return data")
    void findById_WithCorrectReturnData() {
        Mockito
                .doReturn(Optional.ofNullable(serviceType))
                .when(serviceTypeRepo)
                .findById(id);
        ServiceType createdServiceType = serviceTypeRepo.findById(id).get();
        Mockito.verify(serviceTypeRepo, Mockito.times(1)).findById(id);
        assert serviceType != null;
        Assertions.assertEquals(createdServiceType.getId(), serviceType.getId());
    }


    @Test
    @DisplayName("Find all services in page")
    void findAll_Test() {
        Mockito
                .doReturn(page)
                .when(serviceTypeRepo)
                .findAll(pageable);
        Page<ServiceType> serviceTypes = serviceTypeService.getAll(pageable);
        Mockito.verify(serviceTypeRepo, Mockito.times(1)).findAll(pageable);
        Assertions.assertEquals(page, serviceTypes);
    }

    @Test
    @DisplayName("Find service types with EntityNotFoundException")
    void findById_WithNotFoundReturnValue() {
        Mockito
                .doReturn(Optional.empty())
                .when(serviceTypeRepo)
                .findById(id);
        Assertions.assertNotNull(
                Assertions
                        .assertThrows(EntityNotFoundException.class, () -> serviceTypeService.getServiceTypeById(id))
        );
        Mockito.verify(serviceTypeRepo, Mockito.times(1)).findById(id);
    }
}
