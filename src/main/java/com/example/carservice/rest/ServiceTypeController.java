package com.example.carservice.rest;

import com.example.carservice.dto.serviceType.ServiceTypeDTO;
import com.example.carservice.dto.serviceType.ServiceTypeSaveDTO;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.services.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Контроллер для добавления услуг или их просмотра
 * */
@RestController
@RequestMapping("/api/v1/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ModelMapper modelMapper;
    private final ServiceTypeService serviceTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceTypeDTO add(@Valid @RequestBody ServiceTypeSaveDTO serviceTypeSaveDTO){
        ServiceType serviceType = modelMapper.map(serviceTypeSaveDTO, ServiceType.class);
        return modelMapper.map(serviceTypeService.add(serviceType), ServiceTypeDTO.class);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ServiceTypeDTO> getAll(@PageableDefault Pageable pageable){
        Page<ServiceType> serviceTypes = serviceTypeService.getAll(pageable);
        return serviceTypes.map(s -> modelMapper.map(s, ServiceTypeDTO.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceTypeDTO getServiceTypeById(@PathVariable Long id){
        return modelMapper.map(serviceTypeService.getServiceTypeById(id), ServiceTypeDTO.class);
    }
}
