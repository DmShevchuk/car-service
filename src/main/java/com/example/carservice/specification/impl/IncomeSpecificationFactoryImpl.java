package com.example.carservice.specification.impl;

import com.example.carservice.entities.Order;
import com.example.carservice.entities.Order_;
import com.example.carservice.entities.ServiceType_;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.services.OrderStatusService;
import com.example.carservice.specification.IncomeSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class IncomeSpecificationFactoryImpl implements IncomeSpecificationFactory {
    private final SessionFactory sessionFactory;
    private final OrderStatusService orderStatusService;


    @Override
    public Long getIncome(LocalTime timeFrom,
                          LocalTime timeUntil,
                          LocalDate dateFrom,
                          LocalDate dateUntil) {

        EntityManager entityManager = sessionFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> tupleQuery = cb.createQuery(Long.class);
        Root<Order> orderRoot = tupleQuery.from(Order.class);

        Predicate predicate = createPredicateForIncome(cb, orderRoot,
                timeFrom, timeUntil,
                dateFrom, dateUntil);

        tupleQuery.where(predicate);
        tupleQuery.select(cb.sum(orderRoot.get(Order_.serviceType).get(ServiceType_.price)));

        return entityManager.createQuery(tupleQuery).getSingleResult();
    }


    @Override
    public Predicate createPredicateForIncome(CriteriaBuilder cb,
                                              Root<Order> orderRoot,
                                              LocalTime timeFrom,
                                              LocalTime timeUntil,
                                              LocalDate dateFrom,
                                              LocalDate dateUntil) {
        Predicate predicate = cb.conjunction();
        if (timeFrom != null) {
            predicate = cb.and(predicate,
                    cb.greaterThanOrEqualTo(orderRoot.get(Order_.timeStart), timeFrom));
        }

        if (timeUntil != null) {
            predicate = cb.and(predicate,
                    cb.lessThanOrEqualTo(orderRoot.get(Order_.timeStart), timeUntil));
        }

        if (dateFrom != null) {
            predicate = cb.and(predicate,
                    cb.greaterThanOrEqualTo(orderRoot.get(Order_.dateStart), dateFrom));
        }

        if (dateUntil != null) {
            predicate = cb.and(predicate,
                    cb.lessThanOrEqualTo(orderRoot.get(Order_.dateStart), dateUntil));
        }

        predicate = cb.and(predicate,
                cb.equal(orderRoot.get(Order_.orderStatus),
                        orderStatusService.getOrderStatusByName(OrderStatusEnum.FINISHED.toString())));
        return predicate;
    }
}
