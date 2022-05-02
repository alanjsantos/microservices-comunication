package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.repository.ProductRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityViolationException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    public Product save (Product product) {
        var sup = supplierService.getById(product.getSupplier().getId());
        var cat = categoryService.getById(product.getCategory().getId());

        if (product.getQuantityAvailable() <= 0) {
            throw new DataIntegrityViolationException("The quantity should not be less or equal to zero");
        }
        product.setCategory(cat);
        product.setSupplier(sup);
        return productRepository.save(product);
    }

    public List<Product> getAll () {
        return productRepository.findAll();
    }

    public List<Product> getName (String name) {
        List<Product> list = productRepository.findByNameIgnoreCaseContaining(name);

        return list;
    }

    public List<Product> getBySupplierId(Long id) {
        List<Product> list = productRepository.findBySupplierId(id);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This SupplierId " + id + " not aready exists in the Database");
        }
        return list;
    }

    public List<Product> getByCategoryId(Long id) {
        List<Product> list = productRepository.findByCategoryId(id);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This CategoryId " + id + "not aready exists in the Database");
        }

        return list;
    }




}
