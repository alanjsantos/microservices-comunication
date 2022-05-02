package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.Supplier;
import com.alanjsantos.productapi.repository.SupplierRepository;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    public Supplier save(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier getById (Long id) {
        Optional<Supplier> obj =
                repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID " + "This ID not aready exists in the Database"));
    }

    public List<Supplier> getAll () {
        return repository.findAll();
    }


    public List<Supplier> getName (String name) {
        List<Supplier> list = repository.findByNameIgnoreCaseContaining(name);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This Name -> " + name + " not aready exists in the Database");
        }

        return list;
    }
}
