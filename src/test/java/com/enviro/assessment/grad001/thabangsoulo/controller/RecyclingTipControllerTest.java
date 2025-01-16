package com.enviro.assessment.grad001.thabangsoulo.controller;

import com.enviro.assessment.grad001.thabangsoulo.entity.RecyclingTip;
import com.enviro.assessment.grad001.thabangsoulo.service.RecyclingTipService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(RecyclingTipController.class)
class RecyclingTipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecyclingTipService recyclingTipService;

    @Test
    @WithMockUser
    void testGetAllTips() throws Exception {
        // Arrange
        List<RecyclingTip> tips = Arrays.asList(
            new RecyclingTip(1L, "Tip 1", "Details about Tip 1"),
            new RecyclingTip(2L, "Tip 2", "Details about Tip 2")
        );
        Mockito.when(recyclingTipService.getAllTips()).thenReturn(tips);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recycling-tips")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].tip").value("Tip 1"))
                .andExpect(jsonPath("$[1].tip").value("Tip 2"));
    }

    @Test
    @WithMockUser
    void testGetTipById() throws Exception {
        // Arrange
        RecyclingTip tip = new RecyclingTip(1L, "Tip 1", "Details about Tip 1");
        Mockito.when(recyclingTipService.getTipById(ArgumentMatchers.anyLong())).thenReturn(tip);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recycling-tips/{id}", 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Tip 1"))
                .andExpect(jsonPath("$.relevance").value("Details about Tip 1"));
    }

    @Test
    @WithMockUser
    void testCreateTip() throws Exception {
        // Arrange
        RecyclingTip tip = new RecyclingTip(1L, "Tip 1", "Details about Tip 1");
        Mockito.when(recyclingTipService.createTip(ArgumentMatchers.any())).thenReturn(tip);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recycling-tips")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tip\":\"Tip 1\",\"relevance\":\"Details about Tip 1\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Tip 1"))
                .andExpect(jsonPath("$.relevance").value("Details about Tip 1"));
    }

    @Test
    @WithMockUser
    void testUpdateTip() throws Exception {
        // Arrange
        RecyclingTip updatedTip = new RecyclingTip(1L, "Updated Tip 1", "Updated details about Tip 1");
        Mockito.when(recyclingTipService.updateTip(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
               .thenReturn(updatedTip);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recycling-tips/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tip\":\"Updated Tip 1\",\"relevance\":\"Updated details about Tip 1\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Updated Tip 1"))
                .andExpect(jsonPath("$.relevance").value("Updated details about Tip 1"));
    }

    @Test
    @WithMockUser
    void testDeleteTip() throws Exception {
        // Arrange
        Long tipId = 1L;
        Mockito.doNothing().when(recyclingTipService).deleteTip(ArgumentMatchers.anyLong());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recycling-tips/{id}", tipId)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
