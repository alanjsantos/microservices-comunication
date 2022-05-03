package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Category;
import com.alanjsantos.productapi.repository.CategoryRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductService productService;

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(Long id) {
        var obj =
                repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("The ID " + " this Category not aready exists in the Database"));
    }

    public Category save (Category category) {
        return repository.save(category);

    }

    public List<Category> getNameDescription(String description) {
        var list = repository.findByDescriptionIgnoreCaseContaining(description);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This description -> " + description + " not aready exists in the Database");
        }

        return list;
    }

    public Category update (Category category) {
        getById(category.getId());
        return repository.save(category);
    }

    public Category delete (Long id) {
        var category = getById(id);
        if (productService.existsByCategoryId(id)) {
            throw new DataIntegrityException("You cannot delete this supplier because it's already defined by a product.");
        }
        repository.deleteById(id);

        return category;
    }

}
