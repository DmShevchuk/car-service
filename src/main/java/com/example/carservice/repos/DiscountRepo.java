package com.example.carservice.repos;

import com.example.carservice.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
}
