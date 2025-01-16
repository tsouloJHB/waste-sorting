package com.enviro.assessment.grad001.thabangsoulo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.enviro.assessment.grad001.thabangsoulo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.thabangsoulo.service.DisposalGuidelineService;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(DisposalGuidelineController.class)
class DisposalGuidelineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DisposalGuidelineService disposalGuidelineService;

    @Test
    @WithMockUser
    void testGetAllGuidelines() throws Exception {
        // Arrange
        List<DisposalGuideline> guidelines = Arrays.asList(
            new DisposalGuideline(1L, "Dispose plastics responsibly", "Plastics"),
            new DisposalGuideline(2L, "Recycle paper waste", "Paper")
        );
        Mockito.when(disposalGuidelineService.getAllGuidelines()).thenReturn(guidelines);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/disposal-guidelines")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].guideline").value("Dispose plastics responsibly"))
                .andExpect(jsonPath("$[0].wasteCategory").value("Plastics"))
                .andExpect(jsonPath("$[1].guideline").value("Recycle paper waste"))
                .andExpect(jsonPath("$[1].wasteCategory").value("Paper"));
    }

    @Test
    @WithMockUser
    void testGetGuidelineById() throws Exception {
        // Arrange
        DisposalGuideline guideline = new DisposalGuideline(1L, "Dispose plastics responsibly", "Plastics");
        Mockito.when(disposalGuidelineService.getGuidelineById(ArgumentMatchers.anyLong())).thenReturn(guideline);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/disposal-guidelines/{id}", 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value("Dispose plastics responsibly"))
                .andExpect(jsonPath("$.wasteCategory").value("Plastics"));
    }

    @Test
    @WithMockUser
    void testCreateGuideline() throws Exception {
        // Arrange
        DisposalGuideline guideline = new DisposalGuideline(1L, "Dispose plastics responsibly", "Plastics");
        Mockito.when(disposalGuidelineService.createGuideline(ArgumentMatchers.any())).thenReturn(guideline);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/disposal-guidelines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"guideline\":\"Dispose plastics responsibly\",\"wasteCategory\":\"Plastics\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value("Dispose plastics responsibly"))
                .andExpect(jsonPath("$.wasteCategory").value("Plastics"));
    }

    @Test
    @WithMockUser
    void testUpdateGuideline() throws Exception {
        // Arrange
        DisposalGuideline updatedGuideline = new DisposalGuideline(1L, "Recycle plastics", "Plastics");
        Mockito.when(disposalGuidelineService.updateGuideline(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
               .thenReturn(updatedGuideline);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/disposal-guidelines/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"guideline\":\"Recycle plastics\",\"wasteCategory\":\"Plastics\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideline").value("Recycle plastics"))
                .andExpect(jsonPath("$.wasteCategory").value("Plastics"));
    }

    @Test
    @WithMockUser
    void testDeleteGuideline() throws Exception {
        // Arrange
        Long guidelineId = 1L;
        Mockito.doNothing().when(disposalGuidelineService).deleteGuideline(ArgumentMatchers.anyLong());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/disposal-guidelines/{id}", guidelineId)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}


