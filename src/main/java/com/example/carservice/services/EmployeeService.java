package com.example.carservice.services;

import com.example.carservice.dto.employee.EmployeeSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Discount;
import com.example.carservice.entities.Employee;
import com.example.carservice.entities.User;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.EmployeeRepo;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с операторами
 * */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final UserService userService;
    private final BoxService boxService;


    /**
     * Добавление в систему нового оператора
     * */
    @Transactional
    public Employee add(EmployeeSaveDTO employeeDTO){
        Employee employee = new Employee();
        User user = userService.getUserById(employeeDTO.getUserId());
        userService.changeRole(user.getId(), Role.ROLE_OPERATOR);
        employee.setUser(user);
        employee.setBox(boxService.getBoxById(employeeDTO.getBoxId()));
        return employeeRepo.save(employee);
    }


    /**
     * Назначение оператору скидки
     * */
    @Transactional
    public void addDiscount(Employee employee, Discount discount){
        employee.setDiscount(discount);
        employeeRepo.save(employee);
    }


    /**
     * Изменение бокса у оператора
     * */
    @Transactional
    public Employee changeBox(Long employeeId, Long boxId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setBox(boxService.getBoxById(boxId));
        return employeeRepo.save(employee);
    }


    /**
     * Получение списка всех операторов
     **/
    public Page<Employee> getAll(Pageable pageable){
        return employeeRepo.findAll(pageable);
    }



    /**
     * Получение оператора по id
     * */
    public Employee getEmployeeById(Long id){
        return employeeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee", id));
    }



    /**
     * Удаление оператора, при этом пользователь не удаляется
     * */
    @Transactional
    public void removeEmployee(Long id){
        employeeRepo.deleteById(id);
    }



    /**
     * Получение бокса, в котором работает оператор
     * */
    public Box getEmployeeBox(Long id) {
        Employee employee = getEmployeeById(id);
        if (employee.getBox() == null){
            throw new RuntimeException("Unable to get box!");
        }
        return employee.getBox();
    }



    /**
     * Получение скидки, предоставленной оператору
     * */
    public Discount getEmployeeDiscount(Long id){
        Employee employee = getEmployeeById(id);
        if (employee.getDiscount() == null){
            throw new RuntimeException("Unable to get discount!");
        }
        return employee.getDiscount();
    }
}
