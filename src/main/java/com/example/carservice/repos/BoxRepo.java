package com.example.carservice.repos;

import com.example.carservice.entities.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepo extends JpaRepository<Box, Long>, JpaSpecificationExecutor<Box> {
    boolean existsByName(String name);
}
