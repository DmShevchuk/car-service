package com.example.carservice.security;

import com.example.carservice.entities.Employee;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepo userRepo;
    private final OrderService orderService;
    private final BoxService boxService;

    public boolean canGetInfo(Principal principal, Long employeeId) {
        User user = userRepo.findUserByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));
        return user.getEmployee()
                .getId()
                .equals(employeeId);
    }

    public boolean canChangeOrder(Principal principal, Long orderId) {
        User user = userRepo.findUserByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));

        Order order = orderService.getOrderById(orderId);

        if (user.getRole().equals(Role.ROLE_USER)) {
            return order.getUser().equals(user);
        }

        Employee employee = user.getEmployee();
        return employee.getBox().equals(order.getBox());

    }
}
