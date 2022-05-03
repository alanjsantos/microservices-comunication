package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Supplier;
import com.alanjsantos.productapi.model.dto.ProductDTO;
import com.alanjsantos.productapi.model.dto.SupplierDTO;
import com.alanjsantos.productapi.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<SupplierDTO> save (@Valid @RequestBody SupplierDTO dto) {
        var supplier =
                service.save(modelMapper.map(dto, Supplier.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(supplier, SupplierDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplierDTO> getId (@PathVariable Long id) {
        var supplier = service.getById(id);
        var dto = modelMapper.map(supplier, SupplierDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAll () {
        var body =
                service.getAll().stream()
                        .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        var body =
                service.getName(name).stream()
                        .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<SupplierDTO> update (@Valid @RequestBody SupplierDTO dto, @PathVariable Long id) {
        dto.setId(id);
        var supplier =
                service.save(modelMapper.map(dto, Supplier.class));
        return ResponseEntity.ok(modelMapper.map(supplier, SupplierDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SupplierDTO> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }



}
