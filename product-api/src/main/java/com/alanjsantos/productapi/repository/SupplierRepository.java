package com.alanjsantos.productapi.repository;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByNameIgnoreCaseContaining(String name);
}
