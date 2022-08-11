package com.example.carservice.services;

import com.example.carservice.dto.discount.DiscountSaveDTO;
import com.example.carservice.entities.Discount;
import com.example.carservice.entities.Employee;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.exceptions.data.IncorrectDiscountValueException;
import com.example.carservice.repos.DiscountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для назначения минимальной и максимальной скидки
 * */
@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepo discountRepo;
    private final EmployeeService employeeService;


    /**
     * Назначение новой скидки
     * */
    @Transactional
    public Discount add(DiscountSaveDTO discountSaveDTO) {
        if (discountSaveDTO.getMinDiscount() > discountSaveDTO.getMaxDiscount()) {
            throw new IncorrectDiscountValueException();
        }
        Employee employee = employeeService.getEmployeeById(discountSaveDTO.getEmployeeId());
        if (employee.getDiscount() != null) {
            remove(employee.getDiscount().getId());
        }
        Discount discount = new Discount();
        discount.setMinDiscount(discountSaveDTO.getMinDiscount());
        discount.setMaxDiscount(discountSaveDTO.getMaxDiscount());
        discount = discountRepo.save(discount);
        employeeService.addDiscount(employee, discount);
        return discount;
    }


    /**
     * Обновление текущей скидки
     * */
    @Transactional
    public Discount update(Long id, Discount discount) {
        discount.setId(id);
        return discountRepo.save(discount);
    }


    /**
     * Получение всех существующих скидок
     * */
    public Page<Discount> getAll(Pageable pageable) {
        return discountRepo.findAll(pageable);
    }


    /**
     * Удаление скидки
     * */
    @Transactional
    public void remove(Long id) {
        discountRepo.deleteById(id);
    }


    /**
     * Получение скидки по id
     * */
    public Discount getDiscountById(Long id) {
        return discountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount", id));
    }
}
