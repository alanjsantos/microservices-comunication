package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Category;
import com.alanjsantos.productapi.model.dto.CategoryDTO;
import com.alanjsantos.productapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CategoryDTO> save (@Valid @RequestBody CategoryDTO dto) {
        Category category = service.save(modelMapper.map(dto, Category.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(category, CategoryDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll () {
        List<CategoryDTO> body =
                service.getAll().stream()
                        .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getId(@PathVariable Long id) {
        Category category = service.getById(id);
        CategoryDTO dto = modelMapper.map(category, CategoryDTO.class);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("description/{description}")
    public ResponseEntity<?> getByDescription(@Valid @PathVariable String description) {
        List<CategoryDTO> body =
                service.getNameDescription(description).stream()
                        .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }
}

