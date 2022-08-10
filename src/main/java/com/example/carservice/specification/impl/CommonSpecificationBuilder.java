package com.example.carservice.specification.impl;

import com.example.carservice.entities.Order;
import com.example.carservice.entities.Order_;
import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;

@Component
@RequiredArgsConstructor
public class CommonSpecificationBuilder {
    public Specification<Order> getUserOrders(User user, OrderStatusEnum orderStatus) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.equal(root.get(Order_.user), user));
            return cb.and(predicate, cb.equal(root.get(Order_.orderStatus.getName()), orderStatus));
        };
    }
}
