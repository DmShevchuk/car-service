package com.example.carservice.repos;

import com.example.carservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Modifying
    @Query(value = """
            UPDATE orders
            SET id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'CANCELED')
            WHERE
            (date_start + time_start) <= make_timestamp(:y, :mon, :d, :h, :min, 0)
            AND
            id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'CONFIRMED')
            """, nativeQuery = true)
    void updateConfirmedToCanceled(@Param("h") int hour,
                                   @Param("min") int minutes,
                                   @Param("y") int year,
                                   @Param("mon") int month,
                                   @Param("d") int day);

    @Modifying
    @Query(value = """
            UPDATE orders
            SET id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'IN_PROGRESS')
            WHERE
            (date_start + time_start) <= make_timestamp(:y, :mon, :d, :h, :min, 0)
            AND
            (date_end + time_end) > make_timestamp(:y, :mon, :d, :h, :min, 0)
            AND
            id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'CLIENT_CHECKED_IN')
            """, nativeQuery = true)
    void updateCheckedInToInProgress(@Param("h") int hour,
                                     @Param("min") int minutes,
                                     @Param("y") int year,
                                     @Param("mon") int month,
                                     @Param("d") int day);
    @Modifying
    @Query(value = """
            UPDATE orders
            SET id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'FINISHED')
            WHERE
            (date_end + time_end) <= make_timestamp(:y, :mon, :d, :h, :min, 0)
            AND
            id_of_status = (SELECT status_id FROM order_status WHERE status_name = 'IN_PROGRESS')
         """, nativeQuery = true)
    void updateInProgressToFinished(@Param("h") int hour,
                                    @Param("min") int minutes,
                                    @Param("y") int year,
                                    @Param("mon") int month,
                                    @Param("d") int day);
}
