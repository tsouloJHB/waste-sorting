package com.enviro.assessment.grad001.thabangsoulo.service;

import com.enviro.assessment.grad001.thabangsoulo.dto.WasteCategoryDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.repository.WasteCategoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class WasteCategoryService {

    private final WasteCategoryRepository repository;

    public WasteCategoryService(WasteCategoryRepository repository) {
        this.repository = repository;
    }

    public List<WasteCategory> getAllCategories() {
        return repository.findAll();
    }

    public WasteCategory getCategoryById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }


    public WasteCategory createCategory(WasteCategoryDTO dto) {
        // Validate DTO
        if (dto == null || dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
    
        // Check for duplicates
        Optional<WasteCategory> existingCategory = repository.findByName(dto.getName().trim());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + dto.getName() + "' already exists.");
        }
    
        // Map DTO to Entity
        WasteCategory category = new WasteCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    
        // Save Entity
        return repository.save(category);
    }
    

    public WasteCategory updateCategory(Long id, WasteCategory updatedCategory) {
        WasteCategory category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        return repository.save(category);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
