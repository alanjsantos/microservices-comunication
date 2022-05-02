package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Category;
import com.alanjsantos.productapi.model.dto.CategoryDTO;
import com.alanjsantos.productapi.repository.CategoryRepository;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(Long id) {
        Optional<Category> obj =
                repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID" + id + "This ID not aready exists in the Database"));
    }

    public Category save (Category category) {
        return repository.save(category);

    }

    public List<Category> getNameDescription(String description) {
        List<Category> list = repository.findByDescriptionIgnoreCaseContaining(description);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This description -> " + description + " not aready exists in the Database");
        }

        return list;
    }

}
