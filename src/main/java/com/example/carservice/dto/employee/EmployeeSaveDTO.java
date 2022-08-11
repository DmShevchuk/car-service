package com.example.carservice.dto.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeSaveDTO {
    @NotNull(message = "User id not specified!")
    private Long userId;

    @NotNull(message = "Box id not specified!")
    private Long boxId;
}
