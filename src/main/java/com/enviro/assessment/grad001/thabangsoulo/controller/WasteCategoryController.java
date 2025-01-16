package com.enviro.assessment.grad001.thabangsoulo.controller;

import com.enviro.assessment.grad001.thabangsoulo.dto.WasteCategoryDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.service.WasteCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/waste-categories")
public class WasteCategoryController {

    private final WasteCategoryService service;

    public WasteCategoryController(WasteCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<WasteCategory>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategory> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<WasteCategory> createCategory(@Valid @RequestBody WasteCategoryDTO category) {
        return ResponseEntity.ok(service.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCategory> updateCategory(@PathVariable Long id, @Valid @RequestBody WasteCategory category) {
        return ResponseEntity.ok(service.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
