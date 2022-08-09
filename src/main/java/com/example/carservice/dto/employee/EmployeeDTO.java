package com.example.carservice.dto.employee;

import com.example.carservice.dto.box.BoxDTO;
import com.example.carservice.dto.discount.DiscountDTO;
import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.entities.Employee;
import com.example.carservice.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private Long userId;
    private Long boxId;
    private DiscountDTO discount;

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setUserId(employee.getUser().getId());
        if (employee.getBox() != null){
            employeeDTO.setBoxId(employee.getBox().getId());
        }
        if (employee.getDiscount() != null){
            employeeDTO.setDiscount(DiscountDTO.toDTO(employee.getDiscount()));
        }
        return employeeDTO;
    }
}
