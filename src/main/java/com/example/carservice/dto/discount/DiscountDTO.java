package com.example.carservice.dto.discount;

import com.example.carservice.entities.Discount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountDTO {
    private Long id;
    private Float minDiscount;
    private Float maxDiscount;

    public static DiscountDTO toDTO(Discount discount){
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setId(discount.getId());
        discountDTO.setMinDiscount(discount.getMinDiscount());
        discountDTO.setMaxDiscount(discount.getMaxDiscount());
        return discountDTO;
    }
}
