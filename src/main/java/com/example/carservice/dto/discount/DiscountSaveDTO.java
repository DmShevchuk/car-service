package com.example.carservice.dto.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DiscountSaveDTO {
    @NotNull(message = "Minimum discount should be specified!")
    @Min(value = 0)
    @Max(value = 100)
    private Float minDiscount;

    @NotNull(message = "Maximum discount should be specified!")
    @Min(value = 0)
    @Max(value = 100)
    private Float maxDiscount;

    @NotNull(message = "Employee id should be specified!")
    private Long employeeId;
}
