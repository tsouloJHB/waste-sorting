package com.enviro.assessment.grad001.thabangsoulo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;




import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.service.WasteCategoryService;

@WebMvcTest(WasteCategoryController.class)
class WasteCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WasteCategoryService wasteCategoryService;

    @Test
    @WithMockUser // Simulates an authenticated user
    void testCreateWasteCategory() throws Exception {
        // Arrange
        WasteCategory category = new WasteCategory(1L, "Plastic", "Yellow Bin");
        Mockito.when(wasteCategoryService.createCategory(ArgumentMatchers.any()))
               .thenReturn(category);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/waste-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Plastic\",\"description\":\"Yellow Bin\"}")
                .with(csrf())) // Add CSRF token for POST requests
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Plastic"))
                .andExpect(jsonPath("$.description").value("Yellow Bin"));
    }

    //test update category
    @Test
    @WithMockUser // Simulates an authenticated user
    void testUpdateWasteCategory() throws Exception {
        // Arrange
        //WasteCategory existingCategory = new WasteCategory(1L, "Plastic", "Yellow Bin");
        WasteCategory updatedCategory = new WasteCategory(1L, "Plastic", "Green Bin");

        // Mock service to return the updated category when the update method is called
        Mockito.when(wasteCategoryService.updateCategory(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
               .thenReturn(updatedCategory);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/waste-categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Plastic\",\"description\":\"Green Bin\"}")
                .with(csrf())) // Add CSRF token for PUT requests
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Plastic"))
                .andExpect(jsonPath("$.description").value("Green Bin"));
    }


    @Test
    @WithMockUser // Simulates an authenticated user
    void testGetWasteCategoryById() throws Exception {
        // Arrange
        WasteCategory category = new WasteCategory(1L, "Plastic", "Yellow Bin");

        // Mock service to return the category when getCategoryById is called
        Mockito.when(wasteCategoryService.getCategoryById(ArgumentMatchers.anyLong()))
               .thenReturn(category);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/waste-categories/{id}", 1L)
                .with(csrf())) // Add CSRF token for GET requests
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Plastic"))
                .andExpect(jsonPath("$.description").value("Yellow Bin"));
    }

    @Test
    @WithMockUser // Simulates an authenticated user
    void testDeleteWasteCategory() throws Exception {
        // Arrange: The ID to delete
        Long categoryId = 1L;

        // Mock the service deleteCategory method
        Mockito.doNothing().when(wasteCategoryService).deleteCategory(ArgumentMatchers.anyLong());

        // Act & Assert: Perform the DELETE request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/waste-categories/{id}", categoryId)
                .with(csrf())) // Add CSRF token for DELETE requests
                .andExpect(status().isNoContent()); // Expecting HTTP status 204 No Content
    }
}






