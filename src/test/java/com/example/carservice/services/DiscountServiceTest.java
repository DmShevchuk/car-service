package com.example.carservice.services;

import com.example.carservice.entities.Discount;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.DiscountRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DiscountServiceTest {
    @Mock
    private DiscountRepo discountRepo;
    private EmployeeService employeeService;

    private final DiscountService discountService;
    private final Discount discount;

    private final Long discountId = 1L;

    Set<Long> discountIds;
    Pageable pageable;
    Page<Discount> page;

    public DiscountServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.discountService = new DiscountService(discountRepo, employeeService);

        Float minDiscount = 2.0F;
        Float maxDiscount = 10.0F;

        discount = new Discount();
        discount.setId(discountId);
        discount.setMinDiscount(minDiscount);
        discount.setMaxDiscount(maxDiscount);

        discountIds = Set.of(discount.getId());
        pageable = PageRequest.of(0, 5);
        page = new PageImpl<>(List.of(discount));
    }


    @Test
    @DisplayName("Find discount with correct return data")
    void findById_WithCorrectReturnData() {
        Mockito
                .doReturn(Optional.ofNullable(discount))
                .when(discountRepo)
                .findById(discountId);
        Discount createdDiscount = discountRepo.findById(discountId).get();
        Mockito.verify(discountRepo, Mockito.times(1)).findById(discountId);
        Assertions.assertEquals(createdDiscount.getId(), discount.getId());
    }


    @Test
    @DisplayName("Find all discounts in page")
    void findAll_Test() {
        Mockito
                .doReturn(page)
                .when(discountRepo)
                .findAll(pageable);
        Page<Discount> discounts = discountService.getAll(pageable);
        Mockito.verify(discountRepo, Mockito.times(1)).findAll(pageable);
        Assertions.assertEquals(page, discounts);
    }


    @Test
    @DisplayName("Find discount with EntityNotFoundException")
    void findById_WithNotFoundReturnValue() {
        Mockito
                .doReturn(Optional.empty())
                .when(discountRepo)
                .findById(discountId);
        Assertions.assertNotNull(
                Assertions
                        .assertThrows(EntityNotFoundException.class, () -> discountService.getDiscountById(discountId))
        );
        Mockito.verify(discountRepo, Mockito.times(1)).findById(discountId);
    }
}
