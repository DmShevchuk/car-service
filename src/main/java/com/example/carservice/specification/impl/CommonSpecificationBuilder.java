package com.example.carservice.specification.impl;

import com.example.carservice.entities.*;
import com.example.carservice.entities.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;

@Component
@RequiredArgsConstructor
public class CommonSpecificationBuilder {
    public Specification<Order> getUserOrders(User user, OrderStatus orderStatus) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.equal(root.get(Order_.user), user));
            return cb.and(predicate, cb.equal(root.get(Order_.orderStatus), orderStatus));
        };
    }

    public Specification<Order> getBoxOrders(Box box) {
        return (root, query, cb) -> cb.equal(root.get(Order_.box), box);
    }
}
