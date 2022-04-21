package com.alanjsantos.productapi.controller;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.dto.ProductDTO;
import com.alanjsantos.productapi.model.dto.ProductResponse;
import com.alanjsantos.productapi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity save (@Valid @RequestBody ProductDTO dto) {
        Product product =
                service.save(modelMapper.map(dto, Product.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(product, ProductResponse.class));
    }
}
