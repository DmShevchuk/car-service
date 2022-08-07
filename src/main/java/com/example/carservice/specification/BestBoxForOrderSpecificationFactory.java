package com.example.carservice.specification;

import com.example.carservice.entities.Box;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.Date;

public interface BestBoxForOrderSpecificationFactory {
    Specification<Box> getSpecificationForBestBox(Date date,
                                                  LocalTime time,
                                                  LocalTime basicDuration);

    Predicate getPredicateForBestBox(CriteriaBuilder cb,
                                     Root<Box> boxRoot,
                                     Date date,
                                     LocalTime time,
                                     LocalTime basicDuration);
}
