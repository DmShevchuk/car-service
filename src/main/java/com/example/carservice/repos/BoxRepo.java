package com.example.carservice.repos;

import com.example.carservice.entities.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface BoxRepo extends JpaRepository<Box, Long>, JpaSpecificationExecutor<Box> {
    boolean existsByName(String name);

    @Query(value =
            """
            SELECT DISTINCT b.*
            FROM boxes b
            WHERE ((b.start_time <= make_time(:h, :min, 0))
                AND (make_time(:h, :min, 0) + (make_interval(mins => :duration) / b.time_factor)
                         <= b.end_time OR (b.end_time <= b.start_time
                         AND (make_time(:h, :min, 0) + (make_interval(mins => :duration) / b.time_factor)) > b.end_time)
                    OR b.twenty_four_hour = true))

            EXCEPT

            SELECT box.*
            FROM boxes box
                     LEFT JOIN orders ord ON box.box_id = ord.id_of_box
                     LEFT JOIN order_status order_st ON order_st.status_id = ord.id_of_status
            WHERE
                (order_st.status_name NOT IN ('CANCELED', 'FINISHED'))
              AND (ord.date_start >= make_date(:y, :mon, :d))
              AND (ord.date_start <= make_date(:y, :mon, :d + 1))
              AND (
                    (((ord.date_start + ord.time_start) <= make_timestamp(:y, :mon, :d, :h, :min, 0))
                        AND
                     ((make_timestamp(:y, :mon, :d, :h, :min, 0) + (make_interval(mins => :duration) / time_factor))
                         <= (ord.date_end + ord.time_end)))
                    OR ((make_timestamp(:y, :mon, :d, :h, :min, 0) <= (ord.date_start + ord.time_start))
                    AND
                        ((make_timestamp(:y, :mon, :d, :h, :min, 0) + (make_interval(mins => :duration) / time_factor))
                            >= (ord.date_end + ord.time_end))
                        )
                    OR
                    (((make_timestamp(:y, :mon, :d, :h, :min, 0) <= (ord.date_start + ord.time_start))
                        AND
                      ((ord.date_start + ord.time_start) <
                       (make_timestamp(:y, :mon, :d, :h, :min, 0) + (make_interval(mins => :duration) / time_factor)))
                        AND
                      (make_timestamp(:y, :mon, :d, :h, :min, 0) + (make_interval(mins => :duration) / time_factor))
                          <= (ord.date_end + ord.time_end))
                        )
                    OR
                    (make_timestamp(:y, :mon, :d, :h, :min, 0) >= (ord.date_start + ord.time_start)
                        AND
                     (make_timestamp(:y, :mon, :d, :h, :min, 0) < (ord.date_end + ord.time_end))
                        AND
                     ((make_timestamp(:y, :mon, :d, :h, :min, 0) + (make_interval(mins => :duration) / time_factor))
                         >= (ord.date_end + ord.time_end)))
                )
            ORDER BY time_factor DESC
            LIMIT 1
                                   """, nativeQuery = true)
    Optional<Box> findBestBoxForOrder(@Param("h") int hour,
                                      @Param("min") int minutes,
                                      @Param("y") int year,
                                      @Param("mon") int month,
                                      @Param("d") int day,
                                      @Param("duration") int basicDurationInMinutes);
}
