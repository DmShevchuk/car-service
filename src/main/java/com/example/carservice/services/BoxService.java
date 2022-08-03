package com.example.carservice.services;

import com.example.carservice.entities.Box;
import com.example.carservice.exceptions.BoxAlreadyExistsException;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.BoxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepo boxRepo;

    public Box add(Box box){
        if (boxRepo.existsByName(box.getName())){throw new BoxAlreadyExistsException(box.getName());}
        return boxRepo.save(box);
    }

    public Box update(Long id, Box box){
        box.setId(id);
        return boxRepo.save(box);
    }

    public Page<Box> getAll(Pageable pageable){
        return boxRepo.findAll(pageable);
    }

    public void remove(Long id){
        boxRepo.deleteById(id);
    }

    public Box getBoxById(Long id){
        return boxRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Box", id));
    }
}
