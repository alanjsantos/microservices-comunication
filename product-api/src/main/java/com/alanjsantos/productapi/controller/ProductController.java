package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.dto.*;
import com.alanjsantos.productapi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> save (@Valid @RequestBody ProductDTO dto) {
        var product =
                service.save(modelMapper.map(dto, Product.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(product, ProductResponse.class));
    }

    @GetMapping
    public ResponseEntity<?> getAll () {
        var body =
                service.getAll()
                        .stream()
                        .map(entity -> modelMapper.map(entity, ProductResponse.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getId(@PathVariable Long id) {
        var product = service.getId(id);
        var dto = modelMapper.map(product, ProductResponse.class);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        var body =
                service.getName(name).stream()
                        .map(entity -> modelMapper.map(entity, ProductDTO.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("supplierId/{id}")
    public ResponseEntity<?> getBySupplierId(@PathVariable Long id) {
        var body =
                service.getBySupplierId(id).stream()
                        .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("categoryId/{id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable Long id) {
        var body =
                service.getByCategoryId(id).stream()
                        .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductDTO dto, @PathVariable Long id) {
        dto.setId(id);
        var product = service.update(modelMapper.map(dto, Product.class));

        return ResponseEntity.ok().body(modelMapper.map(product, ProductResponse.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductDTO> delete (@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/sales")
    public ResponseEntity<?> findProductSales(@PathVariable Long id) {
        var client = service.findProductSales(id);
        return ResponseEntity.ok().body(client);
    }




}
