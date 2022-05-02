package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.dto.CategoryDTO;
import com.alanjsantos.productapi.model.dto.ProductDTO;
import com.alanjsantos.productapi.model.dto.ProductResponse;
import com.alanjsantos.productapi.model.dto.SupplierDTO;
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
        Product product =
                service.save(modelMapper.map(dto, Product.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(product, ProductResponse.class));
    }

    @GetMapping
    public ResponseEntity<?> getAll () {
        List<ProductResponse> body =
                service.getAll()
                        .stream()
                        .map(entity -> modelMapper.map(entity, ProductResponse.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        List<ProductDTO> body =
                service.getName(name).stream()
                        .map(entity -> modelMapper.map(entity, ProductDTO.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("supplierId/{id}")
    public ResponseEntity<?> getBySupplierId(@PathVariable Long id) {
        List<SupplierDTO> body =
                service.getBySupplierId(id).stream()
                        .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("categoryId/{id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable Long id) {
        List<CategoryDTO> body =
                service.getByCategoryId(id).stream()
                        .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }


}
