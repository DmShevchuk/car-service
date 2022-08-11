package com.example.carservice.rest;

import com.example.carservice.dto.discount.DiscountDTO;
import com.example.carservice.dto.discount.DiscountSaveDTO;
import com.example.carservice.entities.Discount;
import com.example.carservice.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Контроллер для назначения скидок оператору
 * */
@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DiscountController {

    private final DiscountService discountService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DiscountDTO add(@Valid @RequestBody DiscountSaveDTO discountSaveDTO){
        return modelMapper.map(discountService.add(discountSaveDTO), DiscountDTO.class);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DiscountDTO> getAll(@PageableDefault Pageable pageable){
        Page<Discount> discounts = discountService.getAll(pageable);
        return discounts.map(b -> modelMapper.map(b, DiscountDTO.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDTO getDiscountById(@PathVariable Long id){
        return modelMapper.map(discountService.getDiscountById(id), DiscountDTO.class);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDTO update(@PathVariable Long id,
                         @Valid @RequestBody DiscountSaveDTO discountSaveDTO){
        Discount discount = modelMapper.map(discountSaveDTO, Discount.class);
        return modelMapper.map(discountService.update(id, discount), DiscountDTO.class);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id){
        discountService.remove(id);
        return String.format("Discount with id=%d was deleted successfully!", id);
    }
}
