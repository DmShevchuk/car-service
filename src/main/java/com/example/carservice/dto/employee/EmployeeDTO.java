package com.example.carservice.dto.employee;

import com.example.carservice.dto.box.BoxDTO;
import com.example.carservice.dto.discount.DiscountDTO;
import com.example.carservice.dto.user.UserDTO;
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
    private BoxDTO box;
    private DiscountDTO discount;

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        if (employee.getBox() != null){
            employeeDTO.setBox(BoxDTO.toDTO(employee.getBox()));
        }
        if (employee.getDiscount() != null){
            employeeDTO.setDiscount(DiscountDTO.toDTO(employee.getDiscount()));
        }
        return employeeDTO;
    }
}
