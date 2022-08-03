package com.example.carservice.rest;

import com.example.carservice.dto.box.BoxDTO;
import com.example.carservice.dto.box.BoxSaveDTO;
import com.example.carservice.entities.Box;
import com.example.carservice.services.BoxService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BoxDTO add(@Valid @RequestBody BoxSaveDTO boxSaveDTO){
        Box box = modelMapper.map(boxSaveDTO, Box.class);
        return BoxDTO.toDTO(boxService.add(box));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BoxDTO> getAll(@PageableDefault Pageable pageable){
        Page<Box> boxes = boxService.getAll(pageable);
        return boxes.map(b -> modelMapper.map(b, BoxDTO.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoxDTO getBoxById(@PathVariable Long id){
        return BoxDTO.toDTO(boxService.getBoxById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoxDTO update(@PathVariable Long id,
                         @Valid @RequestBody BoxSaveDTO boxSaveDTO){
        Box box = modelMapper.map(boxSaveDTO, Box.class);
        return BoxDTO.toDTO(boxService.update(id, box));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id){
        boxService.remove(id);
        return String.format("Box with id=%d was deleted successfully!", id);
    }
}
