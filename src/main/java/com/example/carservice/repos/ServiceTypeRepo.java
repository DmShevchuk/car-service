package com.example.carservice.repos;

import com.example.carservice.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long>, JpaSpecificationExecutor<ServiceType> {
    boolean existsByServiceName(String name);

    Optional<ServiceType> findServiceTypeByServiceName(String name);
}
