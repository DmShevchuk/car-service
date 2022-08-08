package com.example.carservice.specification;

import com.example.carservice.entities.Order;
import com.example.carservice.entities.enums.OrderStatusEnum;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface IncomeSpecificationFactory {
    /**
     * Получение суммы дохода по указанным критериям <br/>
     * Доход считается как сумма всех выполненных заказов <br/>
     * Т.е. имеющих {@link OrderStatusEnum#FINISHED}
     * */
    Long getIncome(LocalTime timeFrom,
                   LocalTime timeUntil,
                   LocalDate dateFrom,
                   LocalDate dateUntil);



    /**
     * Создание предиката для подсчета доходов <br/>
     *
     * @param cb
     * @param orderRoot
     * @param timeFrom  нижняя граница времени
     * @param timeUntil верхняя граница времени
     * @param dateFrom  нижняя граница даты
     * @param dateUntil верхняя граница даты
     */
    Predicate createPredicateForIncome(
            CriteriaBuilder cb,
            Root<Order> orderRoot,
            LocalTime timeFrom,
            LocalTime timeUntil,
            LocalDate dateFrom,
            LocalDate dateUntil);
}
