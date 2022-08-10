package com.example.carservice.dto.employee;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeBoxDTO {
    @NotNull(message = "Box id not specified!")
    private Long boxId;
}
