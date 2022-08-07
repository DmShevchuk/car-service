package com.example.carservice.specification.impl;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Box_;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.Order_;
import com.example.carservice.specification.BestBoxForOrderSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BestBoxForOrderSpecificationFactoryImpl implements BestBoxForOrderSpecificationFactory {
    private final SessionFactory sessionFactory;

    @Override
    public Specification<Box> getSpecificationForBestBox(Date date,
                                                         LocalTime time,
                                                         LocalTime basicDuration) {
        return (root, query, cb) -> {
            Session session = sessionFactory.openSession();
            CriteriaQuery<Tuple> tupleQuery = cb.createTupleQuery();
            Root<Box> boxRoot = tupleQuery.from(Box.class);
            Join<Box, Order> boxesWithOrders = boxRoot.join(Box_.orders);
            Predicate predicate = getPredicateForBestBox(cb, boxRoot, date, time, basicDuration);

            tupleQuery
                    .where(predicate)
                    .multiselect(
                            boxRoot.get(Box_.id),
                            boxRoot.get(Box_.timeFactor)
                    )
                    .orderBy(cb.desc(boxRoot.get(Box_.timeFactor)));

            List<Tuple> result = session
                    .createQuery(tupleQuery)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            if (result.isEmpty()){
                return cb.or();
            }

            Tuple resultTuple = result.get(0);
            Long bestBoxId = resultTuple.get(boxRoot.get(Box_.id));
            return cb.equal(root.get(Box_.id), bestBoxId);
        };
    }

    @Override
    public Predicate getPredicateForBestBox(CriteriaBuilder cb,
                                            Root<Box> boxRoot,
                                            Date date,
                                            LocalTime time,
                                            LocalTime basicDuration) {
        Predicate predicate = cb.conjunction();
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(boxRoot.get(Box_.startWorkTime), time));
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(boxRoot.get(Box_.endWorkTime), time));
        return null;
    }
}

