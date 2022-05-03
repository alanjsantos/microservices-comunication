package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.repository.ProductRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new DataIntegrityException("The quantity should not be less or equal to zero");
        }
        product.setCategory(cat);
        product.setSupplier(sup);
        return productRepository.save(product);
    }

    public List<Product> getAll () {
        return productRepository.findAll();
    }

    public Product getId(Long id) {
        var obj =
                productRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID " + "This ID not aready exists in the Database"));
    }

    public List<Product> getName (String name) {
        var list = productRepository.findByNameIgnoreCaseContaining(name);

        return list;
    }

    public List<Product> getBySupplierId(Long id) {
        var list = productRepository.findBySupplierId(id);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This SupplierId " + id + " not aready exists in the Database");
        }
        return list;
    }

    public List<Product> getByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    public Boolean existsBySupplierId(Long id) {
        return productRepository.existsBySupplierId(id);
    }

    public Boolean existsByCategoryId(Long id) {
        return productRepository.existsByCategoryId(id);
    }

    public Product update(Product product) {
        getId(product.getId());
        var sup = supplierService.getById(product.getSupplier().getId());
        var cat = categoryService.getById(product.getCategory().getId());
        if (product.getQuantityAvailable() <= 0) {
            throw new DataIntegrityException("The quantity should not be less or equal to zero");
        }
        product.setCategory(cat);
        product.setSupplier(sup);

        return productRepository.save(product);
    }

    public Product delete (Long id) {
         var product = getId(id);
         productRepository.deleteById(id);

        return product;
    }



}
