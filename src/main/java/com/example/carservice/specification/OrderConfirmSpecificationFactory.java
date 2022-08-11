package com.example.carservice.specification;

import com.example.carservice.entities.Confirmation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Поиск подтверждений, у которых истек срок действия (15 минут)
 * */
public interface OrderConfirmSpecificationFactory {
    Specification<Confirmation> createSpecificationForOrderConfirm(LocalDateTime currentTimestamp);
}
