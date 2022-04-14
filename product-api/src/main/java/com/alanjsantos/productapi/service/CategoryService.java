package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Category;
import com.alanjsantos.productapi.repository.CategoryRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityViolationException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(Integer id) {
        Optional<Category> obj =
                repository.findById(id.longValue());
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID" + id + "This ID not aready exists in the DataBase"));
    }

    public Category save (Category category) {
//        getById(category.getId());
        return repository.save(category);

    }

}
