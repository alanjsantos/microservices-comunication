package com.alanjsantos.productapi.repository;

import com.alanjsantos.productapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findByDescriptionIgnoreCaseContaining(@Param("description") String description);
}
