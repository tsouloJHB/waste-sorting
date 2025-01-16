package com.enviro.assessment.grad001.thabangsoulo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.thabangsoulo.entity.RecyclingTip;
import com.enviro.assessment.grad001.thabangsoulo.repository.RecyclingTipRepository;

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
class RecyclingTipIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecyclingTipRepository recyclingTipRepository;

    @BeforeEach
    void setup() {
        recyclingTipRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    void testGetAllTips() throws Exception {
        // Arrange: Add some recycling tips
        RecyclingTip tip1 = new RecyclingTip(null, "Tip 1", "Description 1");
        RecyclingTip tip2 = new RecyclingTip(null, "Tip 2", "Description 2");
        recyclingTipRepository.saveAll(List.of(tip1, tip2));

        // Act & Assert: Retrieve all tips
        mockMvc.perform(get("/api/v1/recycling-tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].tip").value("Tip 1"))
                .andExpect(jsonPath("$[0].relevance").value("Description 1"))
                .andExpect(jsonPath("$[1].tip").value("Tip 2"))
                .andExpect(jsonPath("$[1].relevance").value("Description 2"));
    }

    @Test
    void testGetTipById() throws Exception {
        // Arrange: Add a recycling tip
        RecyclingTip tip = new RecyclingTip(null, "Tip 1", "Description 1");
        RecyclingTip savedTip = recyclingTipRepository.save(tip);

        // Act & Assert: Retrieve the tip by ID
        mockMvc.perform(get("/api/v1/recycling-tips/{id}", savedTip.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Tip 1"))
                .andExpect(jsonPath("$.relevance").value("Description 1"));
    }

    @Test
    void testCreateTip() throws Exception {
        // Arrange: Prepare request body
        String requestBody = "{\"tip\":\"Tip 1\",\"relevance\":\"Description 1\"}";

        // Act & Assert: Create a new recycling tip
        mockMvc.perform(post("/api/v1/recycling-tips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Tip 1"))
                .andExpect(jsonPath("$.relevance").value("Description 1"));
    }

    @Test
    void testUpdateTip() throws Exception {
        // Arrange: Add a recycling tip
        RecyclingTip tip = new RecyclingTip(null, "Tip 1", "Description 1");
        RecyclingTip savedTip = recyclingTipRepository.save(tip);

        // Prepare updated tip JSON
        String updatedTipJson = "{\"tip\":\"Updated Tip\",\"relevance\":\"Updated Description\"}";

        // Act: Update the recycling tip
        mockMvc.perform(put("/api/v1/recycling-tips/{id}", savedTip.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTipJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Updated Tip"))
                .andExpect(jsonPath("$.relevance").value("Updated Description"));
    }

    @Test
    void testDeleteTip() throws Exception {
        // Arrange: Add a recycling tip
        RecyclingTip tip = new RecyclingTip(null, "Tip 1", "Description 1");
        RecyclingTip savedTip = recyclingTipRepository.save(tip);

        // Act: Delete the tip
        mockMvc.perform(delete("/api/v1/recycling-tips/{id}", savedTip.getId()))
                .andExpect(status().isNoContent());

        // Assert: Verify the tip is deleted
        mockMvc.perform(get("/api/v1/recycling-tips/{id}", savedTip.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTipByIdNotFound() throws Exception {
        // Act & Assert: Attempt to get a non-existent tip
        mockMvc.perform(get("/api/v1/recycling-tips/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateTipInvalidRequest() throws Exception {
        // Arrange: Missing required fields
        String invalidRequestBody = "{\"description\":\"Description 1\"}";

        // Act & Assert: Attempt to create an invalid tip
        mockMvc.perform(post("/api/v1/recycling-tips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}

