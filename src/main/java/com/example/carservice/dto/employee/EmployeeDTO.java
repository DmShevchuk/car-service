package com.example.carservice.dto.employee;

import com.example.carservice.dto.box.BoxDTO;
import com.example.carservice.dto.discount.DiscountDTO;
import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private UserDTO user;
    private Long boxId;
    private DiscountDTO discount;

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setUser(UserDTO.toDTO(employee.getUser()));
        if (employee.getBox() != null){
            employeeDTO.setBoxId(employee.getBox().getId());
        }
        if (employee.getDiscount() != null){
            employeeDTO.setDiscount(DiscountDTO.toDTO(employee.getDiscount()));
        }
        return employeeDTO;
    }
}
