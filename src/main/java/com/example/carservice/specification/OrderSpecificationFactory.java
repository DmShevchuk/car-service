package com.example.carservice.specification;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface OrderSpecificationFactory {
    /**
     * Получение Specification<Order> по соответствующим критериям
     * */
    Specification<Order> getSpecificationForOrders(Box box,
                                                   LocalTime timeFrom,
                                                   LocalTime timeUntil,
                                                   LocalDate dateFrom,
                                                   LocalDate dateUntil);



    /**
     * Создание предиката для поиска записей <br/>
     *
     * @param cb
     * @param orderRoot
     * @param box бокс, которому должны принадлежать заказы
     * @param timeFrom  нижняя граница времени
     * @param timeUntil верхняя граница времени
     * @param dateFrom  нижняя граница даты
     * @param dateUntil верхняя граница даты
     */
    Predicate createPredicateForOrders(
            CriteriaBuilder cb,
            Root<Order> orderRoot,
            Box box,
            LocalTime timeFrom,
            LocalTime timeUntil,
            LocalDate dateFrom,
            LocalDate dateUntil);
}
