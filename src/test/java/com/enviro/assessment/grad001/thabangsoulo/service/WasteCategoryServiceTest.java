package com.enviro.assessment.grad001.thabangsoulo.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.enviro.assessment.grad001.thabangsoulo.dto.WasteCategoryDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.repository.WasteCategoryRepository;


import static org.junit.jupiter.api.Assertions.*; // Static import for assertions
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;


@ExtendWith(MockitoExtension.class)
class WasteCategoryServiceTest {

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    @InjectMocks
    private WasteCategoryService wasteCategoryService;

    @BeforeEach
    void setup() {
        // Optional setup if needed
    }

    @Test
    void testAddWasteCategory() {
        // Arrange
        WasteCategoryDTO categoryDTO = new WasteCategoryDTO("Plastic", "Yellow Bin");
        WasteCategory savedCategory = new WasteCategory(1L, "Plastic", "Yellow Bin");

        when(wasteCategoryRepository.save(ArgumentMatchers.any(WasteCategory.class)))
                .thenReturn(savedCategory); // Mock save to return the saved entity

        // Act
        WasteCategory result = wasteCategoryService.createCategory(categoryDTO);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Plastic", result.getName());
        assertEquals("Yellow Bin", result.getDescription());
        assertEquals(1L, result.getId());
        verify(wasteCategoryRepository, times(1)).save(ArgumentMatchers.any(WasteCategory.class));
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        WasteCategory category1 = new WasteCategory(1L, "Plastic", "Yellow Bin");
        WasteCategory category2 = new WasteCategory(2L, "Glass", "Green Bin");
        List<WasteCategory> categories = Arrays.asList(category1, category2);
        when(wasteCategoryRepository.findAll()).thenReturn(categories); // Mock the findAll method

        // Act
        List<WasteCategory> result = wasteCategoryService.getAllCategories();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The result size should be 2");
        assertTrue(result.contains(category1), "The result should contain Plastic category");
        assertTrue(result.contains(category2), "The result should contain Glass category");
        verify(wasteCategoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        Long categoryId = 1L;
        WasteCategory category = new WasteCategory(categoryId, "Plastic", "Yellow Bin");
        when(wasteCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // Mock the findById method

        // Act
        WasteCategory result = wasteCategoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Plastic", result.getName(), "The name should be Plastic");
        assertEquals("Yellow Bin", result.getDescription(), "The description should be Yellow Bin");
        verify(wasteCategoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryByIdNotFound() {
        // Arrange
        Long categoryId = 999L;
        when(wasteCategoryRepository.findById(categoryId)).thenReturn(Optional.empty()); // Simulate category not found

        // Act & Assert
        assertThrows(RuntimeException.class, () -> wasteCategoryService.getCategoryById(categoryId), "Category not found");
        verify(wasteCategoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Long categoryId = 1L;
        WasteCategory existingCategory = new WasteCategory(categoryId, "Plastic", "Yellow Bin");
        WasteCategory updatedCategory = new WasteCategory(null, "Glass", "Green Bin");
        when(wasteCategoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory)); // Mock the findById method
        when(wasteCategoryRepository.save(existingCategory)).thenReturn(existingCategory); // Mock the save method

        // Act
        WasteCategory result = wasteCategoryService.updateCategory(categoryId, updatedCategory);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Glass", result.getName(), "The name should be updated to Glass");
        assertEquals("Green Bin", result.getDescription(), "The description should be updated to Green Bin");
        verify(wasteCategoryRepository, times(1)).findById(categoryId);
        verify(wasteCategoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        Long categoryId = 1L;
        doNothing().when(wasteCategoryRepository).deleteById(categoryId); // Mock the delete method

        // Act
        wasteCategoryService.deleteCategory(categoryId);

        // Assert
        verify(wasteCategoryRepository, times(1)).deleteById(categoryId);
    }
    

}

