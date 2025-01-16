package com.enviro.assessment.grad001.thabangsoulo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// import com.enviro.assessment.grad001.thabangsoulo.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.thabangsoulo.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.thabangsoulo.repository.DisposalGuidelineRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisposalGuidelineServiceTest {

    @Mock
    private DisposalGuidelineRepository disposalGuidelineRepository;

    @InjectMocks
    private DisposalGuidelineService disposalGuidelineService;

    @BeforeEach
    void setup() {
      
    }

    @Test
    void testGetAllGuidelines() {
        // Arrange
        DisposalGuideline guideline1 = new DisposalGuideline(1L, "Separate plastics", "Plastic");
        DisposalGuideline guideline2 = new DisposalGuideline(2L, "Dispose of batteries responsibly", "Hazardous");
        List<DisposalGuideline> guidelines = Arrays.asList(guideline1, guideline2);
        when(disposalGuidelineRepository.findAll()).thenReturn(guidelines);

        // Act
        List<DisposalGuideline> result = disposalGuidelineService.getAllGuidelines();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The result size should be 2");
        assertTrue(result.contains(guideline1), "The result should contain the first guideline");
        assertTrue(result.contains(guideline2), "The result should contain the second guideline");
        verify(disposalGuidelineRepository, times(1)).findAll();
    }

    @Test
    void testGetGuidelineById() {
        // Arrange
        Long guidelineId = 1L;
        DisposalGuideline guideline = new DisposalGuideline(guidelineId, "Separate plastics", "Plastic");
        when(disposalGuidelineRepository.findById(guidelineId)).thenReturn(Optional.of(guideline));

        // Act
        DisposalGuideline result = disposalGuidelineService.getGuidelineById(guidelineId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Separate plastics", result.getGuideline());
        assertEquals("Plastic", result.getWasteCategory());
        verify(disposalGuidelineRepository, times(1)).findById(guidelineId);
    }

    @Test
    void testGetGuidelineByIdNotFound() {
        // Arrange
        Long guidelineId = 999L;
        when(disposalGuidelineRepository.findById(guidelineId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> disposalGuidelineService.getGuidelineById(guidelineId), 
                "Disposal Guideline not found");
        verify(disposalGuidelineRepository, times(1)).findById(guidelineId);
    }

    // @Test
    // void testCreateGuideline() {
    //     // Arrange
    //     DisposalGuidelineDTO guidelineDTO = new DisposalGuidelineDTO();
    //     guidelineDTO.setGuideline("Dispose of batteries responsibly");
    //     guidelineDTO.setWasteCategory("Hazardous");
    
    //     DisposalGuideline guideline = new DisposalGuideline(1L, "Dispose of batteries responsibly", "Hazardous");
    //     when(disposalGuidelineRepository.save(any(DisposalGuideline.class))).thenReturn(guideline);
    
    //     // Act
    //     DisposalGuideline result = disposalGuidelineService.createGuideline(guidelineDTO);
    
    //     // Assert
    //     assertNotNull(result, "The result should not be null");
    //     assertEquals("Dispose of batteries responsibly", result.getGuideline());
    //     assertEquals("Hazardous", result.getWasteCategory());
    //     verify(disposalGuidelineRepository, times(1)).save(any(DisposalGuideline.class));
    // }
    

    @Test
    void testUpdateGuideline() {
        // Arrange
        Long guidelineId = 1L;
        DisposalGuideline existingGuideline = new DisposalGuideline(guidelineId, "Separate plastics", "Plastic");
        DisposalGuideline updatedGuideline = new DisposalGuideline(null, "Recycle glass bottles", "Glass");
        when(disposalGuidelineRepository.findById(guidelineId)).thenReturn(Optional.of(existingGuideline));
        when(disposalGuidelineRepository.save(existingGuideline)).thenReturn(existingGuideline);

        // Act
        DisposalGuideline result = disposalGuidelineService.updateGuideline(guidelineId, updatedGuideline);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Recycle glass bottles", result.getGuideline());
        assertEquals("Glass", result.getWasteCategory());
        verify(disposalGuidelineRepository, times(1)).findById(guidelineId);
        verify(disposalGuidelineRepository, times(1)).save(existingGuideline);
    }

    // @Test
    // void testDeleteGuidelineNotFound() {
    //     // Arrange
    //     Long guidelineId = 999L;
    //     doThrow(new ResourceNotFoundException("Disposal Guideline with ID " + guidelineId + " not found"))
    //         .when(disposalGuidelineRepository).deleteById(guidelineId);
    
    //     // Act & Assert
    //     assertThrows(ResourceNotFoundException.class, () -> disposalGuidelineService.deleteGuideline(guidelineId));
    //     verify(disposalGuidelineRepository, times(0)).deleteById(guidelineId);
    // }
    
}

