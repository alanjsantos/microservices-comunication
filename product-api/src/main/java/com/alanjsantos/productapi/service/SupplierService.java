package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Supplier;
import com.alanjsantos.productapi.repository.SupplierRepository;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier getById (Long id) {
        var obj =
                supplierRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("The ID " +  " this Supplier not aready exists in the Database"));
    }

    public List<Supplier> getAll () {
        return supplierRepository.findAll();
    }


    public List<Supplier> getName (String name) {
        var list = supplierRepository.findByNameIgnoreCaseContaining(name);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This Name -> " + name + " not aready exists in the Database");
        }

        return list;
    }

    public Supplier update(Supplier supplier) {
        getById(supplier.getId());
        return supplierRepository.save(supplier);
    }

    public Supplier delete (Long id) {
        var supplierId = getById(id);

        if (productService.existsBySupplierId(id)) {
            throw new DataIntegrityException("You cannot delete this supplier because it's already defined by a product.");
        }
        supplierRepository.deleteById(id);

        return supplierId;
    }


}
