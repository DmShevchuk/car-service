package com.example.carservice.rest;

import com.example.carservice.dto.box.BoxDTO;
import com.example.carservice.dto.discount.DiscountDTO;
import com.example.carservice.dto.employee.ChangeBoxDTO;
import com.example.carservice.dto.employee.EmployeeDTO;
import com.example.carservice.dto.employee.EmployeeSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Discount;
import com.example.carservice.entities.Employee;
import com.example.carservice.security.AccessValidator;
import com.example.carservice.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final AccessValidator accessValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO addNewEmployee(@Valid @RequestBody EmployeeSaveDTO  employeeDTO){
        return EmployeeDTO.toDTO(employeeService.add(employeeDTO));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<EmployeeDTO> getAllEmployees(@PageableDefault Pageable pageable){
        Page<Employee> employees = employeeService.getAll(pageable);
        return employees.map(e -> modelMapper.map(e, EmployeeDTO.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || (hasRole('OPERATOR') && @accessValidator.canGetInfo(#id))")
    public EmployeeDTO getEmployeeById(@PathVariable Long id){
        return EmployeeDTO.toDTO(employeeService.getEmployeeById(id));
    }


    @GetMapping("/{id}/box")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || (hasRole('OPERATOR') && @accessValidator.canGetInfo(#id))")
    public BoxDTO getEmployeeBox(@PathVariable Long id){
        return modelMapper.map(employeeService.getEmployeeBox(id), BoxDTO.class);
    }


    @GetMapping("/{id}/discount")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') || (hasRole('OPERATOR') && @accessValidator.canGetInfo(#id))")
    public DiscountDTO getEmployeeDiscount(@PathVariable Long id){
        return modelMapper.map(employeeService.getEmployeeDiscount(id), DiscountDTO.class);
    }


    @PatchMapping("/{employeeId}/new-box")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO changeBoxForEmployee(@PathVariable Long employeeId,
                                            @Valid @RequestBody ChangeBoxDTO changeBoxDTO){
        return EmployeeDTO.toDTO(employeeService.changeBox(employeeId, changeBoxDTO.getBoxId()));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public String dismissEmployee(@PathVariable Long id){
        employeeService.removeEmployee(id);
        return String.format("Employee with id=%d was delete successfully!", id);
    }
}
