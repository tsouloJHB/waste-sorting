package com.enviro.assessment.grad001.thabangsoulo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.thabangsoulo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.thabangsoulo.repository.WasteCategoryRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
@SpringBootTest
@AutoConfigureMockMvc
class DisposalGuidelineIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DisposalGuidelineRepository disposalGuidelineRepository;

    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    @BeforeEach
    void setup() {
        disposalGuidelineRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    void testGetAllGuidelines() throws Exception {
        // Arrange: Add some disposal guidelines
        DisposalGuideline guideline1 = new DisposalGuideline(null, "Guideline 1", "Category 1");
        DisposalGuideline guideline2 = new DisposalGuideline(null, "Guideline 2", "Category 2");
        disposalGuidelineRepository.saveAll(List.of(guideline1, guideline2));

        // Act & Assert: Retrieve all guidelines
        mockMvc.perform(get("/api/v1/disposal-guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].guideline").value("Guideline 1"))
                .andExpect(jsonPath("$[0].wasteCategory").value("Category 1"))
                .andExpect(jsonPath("$[1].guideline").value("Guideline 2"))
                .andExpect(jsonPath("$[1].wasteCategory").value("Category 2"));
    }

    @Test
    void testGetGuidelineById() throws Exception {
        // Arrange: Add a guideline
        WasteCategory category = new WasteCategory(null, "Plastic", "Yellow Bin");
        WasteCategory savedCategory = wasteCategoryRepository.save(category);

        DisposalGuideline guideline = new DisposalGuideline(null, savedCategory.getName(), "Category 1");
        DisposalGuideline savedGuideline = disposalGuidelineRepository.save(guideline);

        // Act & Assert: Retrieve the guideline by ID
        mockMvc.perform(get("/api/v1/disposal-guidelines/{id}", savedGuideline.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value(savedCategory.getName()))
                .andExpect(jsonPath("$.wasteCategory").value("Category 1"));
    }

    @Test
    void testCreateGuideline() throws Exception {
         WasteCategory category = new WasteCategory(null, "Plastic12234", "Yellow Bin");
        WasteCategory savedCategory = wasteCategoryRepository.save(category);
        // Arrange: Prepare request body
        String requestBody = "{\"guideline\":\"Guideline 1\",\"wasteCategory\":\"" + savedCategory.getName() + "\"}";

        // Act & Assert: Create a new guideline
        mockMvc.perform(post("/api/v1/disposal-guidelines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value("Guideline 1"))
                .andExpect(jsonPath("$.wasteCategory").value(savedCategory.getName() ));
    }

    @Test
    void testUpdateGuideline() throws Exception {
        // Arrange: Add a guideline
        DisposalGuideline guideline = new DisposalGuideline(null, "Guideline 1", "Category 1");
        DisposalGuideline savedGuideline = disposalGuidelineRepository.save(guideline);

        // Prepare updated guideline JSON
        String updatedGuidelineJson = "{\"guideline\":\"Updated Guideline\",\"wasteCategory\":\"Updated Category\"}";

        // Act: Update the guideline
        mockMvc.perform(put("/api/v1/disposal-guidelines/{id}", savedGuideline.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedGuidelineJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value("Updated Guideline"))
                .andExpect(jsonPath("$.wasteCategory").value("Updated Category"));
    }

    @Test
    void testDeleteGuideline() throws Exception {
        // Arrange: Add a guideline
        WasteCategory category = new WasteCategory(null, "Plastic 45", "Yellow Bin");
        WasteCategory savedCategory = wasteCategoryRepository.save(category);

        DisposalGuideline guideline = new DisposalGuideline(null, "Guideline 1", savedCategory.getName());
        DisposalGuideline savedGuideline = disposalGuidelineRepository.save(guideline);

        // Act: Delete the guideline
        mockMvc.perform(delete("/api/v1/disposal-guidelines/{id}", savedGuideline.getId()))
                .andExpect(status().isNoContent());

        // Assert: Verify the guideline is deleted
        mockMvc.perform(get("/api/v1/disposal-guidelines/{id}", savedGuideline.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetGuidelineByIdNotFound() throws Exception {
        // Act & Assert: Attempt to get a non-existent guideline
        mockMvc.perform(get("/api/v1/disposal-guidelines/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateGuidelineInvalidRequest() throws Exception {
        // Arrange: Missing required fields
        String invalidRequestBody = "{\"wasteCategory\":\"Category 1\"}";

        // Act & Assert: Attempt to create an invalid guideline
        mockMvc.perform(post("/api/v1/disposal-guidelines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}

