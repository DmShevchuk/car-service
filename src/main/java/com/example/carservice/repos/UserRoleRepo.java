package com.example.carservice.repos;

import com.example.carservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
