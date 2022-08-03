package com.example.carservice.dto.orderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusSaveDTO {
    @NotBlank(message = "Status name not specified!")
    private String statusName;
}
