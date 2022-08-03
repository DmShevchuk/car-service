package com.example.carservice.dto.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DiscountSaveDTO {
    @NotNull(message = "Minimum discount should be specified!")
    private Float minDiscount;

    @NotNull(message = "Maximum discount should be specified!")
    private Float maxDiscount;

    @NotNull(message = "User id should be specified!")
    private Long userId;
}
