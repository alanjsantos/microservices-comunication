package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.repository.ProductRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    public Product save (Product product) {
        supplierService.getById(product.getSupplier().getId());
        categoryService.getById(product.getCategory().getId());

        if (product.getQuantityAvailable() <= 0) {
            throw new DataIntegrityViolationException("The quantity should not be less or equal to zero");
        }
        return productRepository.save(product);
    }


}
