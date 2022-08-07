package com.example.carservice.repos;

import com.example.carservice.entities.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BoxRepo extends JpaRepository<Box, Long>, JpaSpecificationExecutor<Box> {
    boolean existsByName(String name);

    @Query(value =
            """
                    SELECT DISTINCT b.*
                    FROM boxes b
                    WHERE ((b.start_time <= make_time(:h, :m, 0))
                               AND (make_time(:h, :m, 0) + (make_interval(mins => :duration) / b.time_factor)
                                <= b.end_time)
                        OR b.twenty_four_hour)

                    EXCEPT

                    SELECT DISTINCT box.*
                    FROM boxes box
                             JOIN orders ord ON box.box_id = ord.id_of_box
                             JOIN order_status order_st ON order_st.status_id = ord.id_of_status
                    WHERE order_st.status_name NOT IN ('CANCELED', 'FINISHED')
                      AND ord.date = (:date) --OR ord.date = (:date) + interval '1 day')
                      AND ((ord.time_start <= make_time(:h, :m, 0)) AND (ord.time_end > make_time(:h, :m, 0)))
                      OR ((make_time(:h, :m, 0) + (make_interval(mins => :duration) / time_factor) > ord.time_start)
                       AND (make_time(:h, :m, 0) + (make_interval(mins => :duration) / time_factor) < ord.time_end))
                                        
                    ORDER BY time_factor DESC
                    LIMIT 1
                    """, nativeQuery = true)
    Optional<Box> findBestBoxForOrder(@Param("h") int hour,
                                      @Param("m") int minutes,
                                      @Param("date") Date date,
                                      @Param("duration") int basicDurationInMinutes);
}
