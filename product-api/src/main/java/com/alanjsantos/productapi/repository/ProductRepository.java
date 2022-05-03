package com.alanjsantos.productapi.repository;

import com.alanjsantos.productapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findBySupplierId(Long id);

    List<Product> findByCategoryId(Long id);

    Boolean existsBySupplierId(Long id);

    Boolean existsByCategoryId(Long id);
}
