package com.example.carservice.specification;

import com.example.carservice.entities.Confirmation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public interface OrderConfirmSpecificationFactory {
    Specification<Confirmation> createSpecificationForOrderConfirm(LocalDateTime currentTimestamp);

}
