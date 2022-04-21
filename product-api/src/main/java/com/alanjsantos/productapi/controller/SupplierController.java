package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Supplier;
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
        Supplier supplier =
                service.save(modelMapper.map(dto, Supplier.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(supplier, SupplierDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplierDTO> getId (@PathVariable Long id) {
        Supplier supplier = service.getById(id);
        SupplierDTO dto = modelMapper.map(supplier, SupplierDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAll () {
        List<SupplierDTO> body =
                service.getAll().stream()
                        .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }


}
