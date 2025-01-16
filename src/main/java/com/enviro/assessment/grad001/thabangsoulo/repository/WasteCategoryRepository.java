package com.enviro.assessment.grad001.thabangsoulo.repository;

import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
      // Define the query method to find by name
      Optional<WasteCategory> findByName(String name);
}
