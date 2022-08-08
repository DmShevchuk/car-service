package com.example.carservice.specification.impl;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.Order_;
import com.example.carservice.specification.OrderSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderSpecificationFactoryImpl implements OrderSpecificationFactory {
    @Override
    public Specification<Order> getSpecificationForOrders(Box box,
                                                          LocalTime timeFrom,
                                                          LocalTime timeUntil,
                                                          Date dateFrom,
                                                          Date dateUntil) {

        return (root, query, cb) -> createPredicateForOrders(cb, root,
                box, timeFrom,
                timeUntil, dateFrom, dateUntil);
    }

    @Override
    public Predicate createPredicateForOrders(CriteriaBuilder cb,
                                                    Root<Order> orderRoot,
                                                    Box box,
                                                    LocalTime timeFrom,
                                                    LocalTime timeUntil,
                                                    Date dateFrom,
                                                    Date dateUntil) {
        Predicate predicate = cb.conjunction();
        if (box != null) {
            predicate = cb.and(predicate, cb.equal(orderRoot.get(Order_.box), box));
        }
        if (timeFrom != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(orderRoot.get(Order_.timeStart), timeFrom));
        }
        if (timeUntil != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(orderRoot.get(Order_.timeStart), timeUntil));
        }
        if (dateFrom != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(orderRoot.get(Order_.dateStart), dateFrom));
        }
        if (dateUntil != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(orderRoot.get(Order_.dateStart), dateUntil));
        }
        return predicate;
    }
}
