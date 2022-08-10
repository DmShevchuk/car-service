package com.example.carservice.rest;

import com.example.carservice.dto.employee.EmployeeDTO;
import com.example.carservice.dto.employee.EmployeeSaveDTO;
import com.example.carservice.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO addNewEmployee(@RequestBody EmployeeSaveDTO  employeeDTO){
        return EmployeeDTO.toDTO(employeeService.add(employeeDTO));
    }


    @PatchMapping("/{employeeId}/new-box")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO changeBoxForEmployee(@PathVariable Long employeeId,
                                            @RequestParam("boxId") Long boxId){
        return EmployeeDTO.toDTO(employeeService.changeBox(employeeId, boxId));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String dismissEmployee(@PathVariable Long id){
        employeeService.removeEmployee(id);
        return String.format("Employee with id=%d was delete successfully!", id);
    }
}
