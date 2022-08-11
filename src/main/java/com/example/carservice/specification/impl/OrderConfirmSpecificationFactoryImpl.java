package com.example.carservice.specification.impl;

import com.example.carservice.entities.Confirmation;
import com.example.carservice.entities.Confirmation_;
import com.example.carservice.specification.OrderConfirmSpecificationFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

@Component
public class OrderConfirmSpecificationFactoryImpl implements OrderConfirmSpecificationFactory {
    @Override
    public Specification<Confirmation> createSpecificationForOrderConfirm(LocalDateTime currentTimestamp) {
        return (root, query, cb) -> cb.lessThan(root.get(Confirmation_.expireAt), currentTimestamp);
    }
}
