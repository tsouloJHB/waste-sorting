package com.enviro.assessment.grad001.thabangsoulo.service;

import com.enviro.assessment.grad001.thabangsoulo.dto.RecyclingTipDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.RecyclingTip;
import com.enviro.assessment.grad001.thabangsoulo.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.thabangsoulo.repository.RecyclingTipRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecyclingTipServiceTest {

    @Mock
    private RecyclingTipRepository recyclingTipRepository;

    @InjectMocks
    private RecyclingTipService recyclingTipService;

    @BeforeEach
    void setup() {
        // Optional setup
        
    }

    @Test
    void testGetAllTips() {
        // Arrange
        RecyclingTip tip1 = new RecyclingTip(1L, "Recycle paper to save trees", "High");
        RecyclingTip tip2 = new RecyclingTip(2L, "Reuse glass jars for storage", "Medium");
        List<RecyclingTip> tips = Arrays.asList(tip1, tip2);
        when(recyclingTipRepository.findAll()).thenReturn(tips);

        // Act
        List<RecyclingTip> result = recyclingTipService.getAllTips();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The result size should be 2");
        assertTrue(result.contains(tip1), "The result should contain the first tip");
        assertTrue(result.contains(tip2), "The result should contain the second tip");
        verify(recyclingTipRepository, times(1)).findAll();
    }

    @Test
    void testGetTipById() {
        // Arrange
        Long tipId = 1L;
        RecyclingTip tip = new RecyclingTip(tipId, "Recycle paper to save trees", "High");
        when(recyclingTipRepository.findById(tipId)).thenReturn(Optional.of(tip));

        // Act
        RecyclingTip result = recyclingTipService.getTipById(tipId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Recycle paper to save trees", result.getTip());
        assertEquals("High", result.getRelevance());
        verify(recyclingTipRepository, times(1)).findById(tipId);
    }

    @Test
    void testGetTipByIdNotFound() {
        // Arrange
        Long tipId = 999L;
        when(recyclingTipRepository.findById(tipId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> recyclingTipService.getTipById(tipId), "Recycling Tip not found");
        verify(recyclingTipRepository, times(1)).findById(tipId);
    }

  @Test
    void testCreateTip() {
        // Arrange
        RecyclingTipDTO dto = new RecyclingTipDTO("Recycle paper to save trees", "High");
        RecyclingTip savedTip = new RecyclingTip(1L, "Recycle paper to save trees", "High");
        
        when(recyclingTipRepository.save(any(RecyclingTip.class))).thenReturn(savedTip);

        // Act
        RecyclingTip result = recyclingTipService.createTip(dto);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Recycle paper to save trees", result.getTip());
        assertEquals("High", result.getRelevance());
        assertEquals(1L, result.getId(), "The ID should match the saved entity");
        verify(recyclingTipRepository, times(1)).save(any(RecyclingTip.class));
    }


    @Test
    void testUpdateTip() {
        // Arrange
        Long tipId = 1L;
        RecyclingTip existingTip = new RecyclingTip(tipId, "Recycle paper", "Medium");
        RecyclingTip updatedTip = new RecyclingTip(null, "Recycle glass jars", "High");
        when(recyclingTipRepository.findById(tipId)).thenReturn(Optional.of(existingTip));
        when(recyclingTipRepository.save(existingTip)).thenReturn(existingTip);

        // Act
        RecyclingTip result = recyclingTipService.updateTip(tipId, updatedTip);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Recycle glass jars", result.getTip());
        assertEquals("High", result.getRelevance());
        verify(recyclingTipRepository, times(1)).findById(tipId);
        verify(recyclingTipRepository, times(1)).save(existingTip);
    }

    @Test
    void testDeleteTip() {
        // Arrange
        Long tipId = 1L;
        doNothing().when(recyclingTipRepository).deleteById(tipId);

        // Act
        recyclingTipService.deleteTip(tipId);

        // Assert
        verify(recyclingTipRepository, times(1)).deleteById(tipId);
    }
}
