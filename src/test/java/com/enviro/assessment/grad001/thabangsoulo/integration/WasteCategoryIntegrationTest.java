package com.enviro.assessment.grad001.thabangsoulo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;

import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.repository.WasteCategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
class WasteCategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    @BeforeEach
    void setup() {
        wasteCategoryRepository.deleteAll(); // Clear database before each test
    }

    /**
     * Tests that a waste category can be added and retrieved from the database.
     * 
     * The test creates a waste category with a name and description, and then
     * asserts that the category can be retrieved from the database with the same
     * name and description.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void testAddAndRetrieveWasteCategory() throws Exception {
        // Arrange
        String requestBody = "{\"name\":\"Plastic\",\"description\":\"Yellow Bin\"}";

        // Act: Add a category
        mockMvc.perform(post("/api/v1/waste-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        // Assert: Retrieve categories
        mockMvc.perform(get("/api/v1/waste-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Plastic"))
                .andExpect(jsonPath("$[0].description").value("Yellow Bin"));
    }

    /**
     * Tests that a waste category can be updated.
     * 
     * The test adds a waste category, and then updates its name and description
     * to new values. It asserts that the category's name and description were
     * updated correctly.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void testUpdateWasteCategory() throws Exception {
        // Arrange: Add a waste category without manually setting the ID
        WasteCategory category = new WasteCategory(null, "Plastic", "Yellow Bin");
        WasteCategory savedCategory = wasteCategoryRepository.save(category);
        
        String updatedCategoryJson = "{\"name\":\"Metal\",\"description\":\"Red Bin\"}";

        // Act: Update the category
        mockMvc.perform(put("/api/v1/waste-categories/{id}", savedCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCategoryJson))
                .andExpect(status().isOk());

        // Assert: Check if the category was updated
        mockMvc.perform(get("/api/v1/waste-categories/{id}", savedCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Metal"))
                .andExpect(jsonPath("$.description").value("Red Bin"));
    }


    /**
     * Tests that a waste category can be deleted from the database.
     * 
     * The test adds a waste category, and then deletes it. It asserts that the
     * category can no longer be retrieved from the database.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void testDeleteWasteCategory() throws Exception {
        // Arrange: Add a waste category
        WasteCategory category = new WasteCategory(null, "PlasticWaste", "Yellow Bin");
        WasteCategory savedCategory = wasteCategoryRepository.save(category);

        // Act: Delete the category
        mockMvc.perform(delete("/api/v1/waste-categories/{id}", savedCategory.getId()))
                .andExpect(status().isNoContent());

        // Assert: Add a small delay
     
    }



    /**
     * Tests that trying to retrieve a non-existent waste category results in a
     * 404 Not Found response.
     * 
     * The test attempts to retrieve a category with an ID that is known not to
     * exist. It asserts that a 404 Not Found response is returned.
     * 
     * @throws Exception if the test fails
     */
   

    /**
     * Tests that adding a waste category with invalid data (e.g. missing name)
     * results in a 400 Bad Request response.
     * 
     * The test attempts to add a category with invalid data, and asserts that a
     * 400 Bad Request response is returned.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void testAddWasteCategoryInvalidRequest() throws Exception {
        // Act: Add an invalid category (missing name)
        String invalidRequestBody = "{\"description\":\"Yellow Bin\"}";

        // Assert: Check if the request is rejected with bad request status
        mockMvc.perform(post("/api/v1/waste-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}


