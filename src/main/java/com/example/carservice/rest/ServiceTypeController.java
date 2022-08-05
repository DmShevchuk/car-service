package com.example.carservice.rest;

import com.example.carservice.dto.serviceType.ServiceTypeDTO;
import com.example.carservice.dto.serviceType.ServiceTypeSaveDTO;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.services.ServiceTypeService;
import com.example.carservice.utils.ServiceTypeSaveDtoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;
    private final ModelMapper modelMapper;
    private final ServiceTypeSaveDtoValidator serviceTypeValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceTypeDTO add(@Valid @RequestBody ServiceTypeSaveDTO serviceTypeSaveDTO){
        ServiceType serviceType = serviceTypeValidator.validate(serviceTypeSaveDTO);
        return ServiceTypeDTO.toDTO(serviceTypeService.add(serviceType));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ServiceTypeDTO> getAll(@PageableDefault Pageable pageable){
        Page<ServiceType> serviceTypes = serviceTypeService.getAll(pageable);
        return serviceTypes.map(s -> modelMapper.map(s, ServiceTypeDTO.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypeDTO getServiceTypeById(@PathVariable Long id){
        return ServiceTypeDTO.toDTO(serviceTypeService.getServiceTypeById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypeDTO update(@PathVariable Long id,
                         @Valid @RequestBody ServiceTypeSaveDTO serviceTypeSaveDTO){
        ServiceType serviceType = serviceTypeValidator.validate(serviceTypeSaveDTO);
        return ServiceTypeDTO.toDTO(serviceTypeService.update(id, serviceType));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id){
        serviceTypeService.remove(id);
        return String.format("Service with id=%d was deleted successfully!", id);
    }
}
