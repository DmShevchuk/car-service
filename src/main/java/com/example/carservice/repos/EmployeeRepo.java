package com.example.carservice.repos;

import com.example.carservice.entities.Employee;
import com.example.carservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
