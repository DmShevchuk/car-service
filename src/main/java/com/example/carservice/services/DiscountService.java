package com.example.carservice.services;

import com.example.carservice.entities.Discount;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.DiscountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepo discountRepo;

    public Discount add(Discount discount){
        return discountRepo.save(discount);
    }

    public Discount update(Long id, Discount discount){
        discount.setId(id);
        return discountRepo.save(discount);
    }

    public Page<Discount> getAll(Pageable pageable){
        return discountRepo.findAll(pageable);
    }

    public void remove(Long id){
        discountRepo.deleteById(id);
    }

    public Discount getDiscountById(Long id){
        return discountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount", id));
    }
}
