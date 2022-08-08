package com.example.carservice.services;

import com.example.carservice.dto.employee.EmployeeSaveDTO;
import com.example.carservice.entities.Discount;
import com.example.carservice.entities.Employee;
import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.RoleEnum;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final UserService userService;
    private final BoxService boxService;

    @Transactional
    public Employee add(EmployeeSaveDTO employeeDTO){
        Employee employee = new Employee();
        User user = userService.getUserById(employeeDTO.getUserId());
        userService.changeRole(user.getId(), RoleEnum.ROLE_OPERATOR.toString());
        employee.setUser(user);
        employee.setBox(boxService.getBoxById(employeeDTO.getBoxId()));
        return employeeRepo.save(employee);
    }

    @Transactional
    public void addDiscount(Employee employee, Discount discount){
        employee.setDiscount(discount);
        employeeRepo.save(employee);
    }

    @Transactional
    public Employee changeBox(Long employeeId, Long boxId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setBox(boxService.getBoxById(boxId));
        return employeeRepo.save(employee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee", id));
    }

    @Transactional
    public void removeEmployee(Long id){
        employeeRepo.deleteById(id);
    }
}
