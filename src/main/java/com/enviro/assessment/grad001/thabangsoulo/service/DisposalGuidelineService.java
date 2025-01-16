package com.enviro.assessment.grad001.thabangsoulo.service;

import com.enviro.assessment.grad001.thabangsoulo.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.thabangsoulo.entity.WasteCategory;
import com.enviro.assessment.grad001.thabangsoulo.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.thabangsoulo.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.thabangsoulo.repository.WasteCategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposalGuidelineService {
    private final DisposalGuidelineRepository repository;
     private final WasteCategoryRepository wasteCategoryRepository;

    public DisposalGuidelineService(DisposalGuidelineRepository repository, WasteCategoryRepository wasteCategoryRepository) {
        this.repository = repository;
        this.wasteCategoryRepository = wasteCategoryRepository;
  
    }

    public List<DisposalGuideline> getAllGuidelines() {
        return repository.findAll();
    }

    public DisposalGuideline getGuidelineById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Disposal Guideline with ID " + id + " not found"));
    }


     public DisposalGuideline createGuideline(DisposalGuidelineDTO dto) {
        // Validate that the WasteCategory exists
        Optional<WasteCategory> existingCategory = wasteCategoryRepository.findByName(dto.getWasteCategory());
        if (existingCategory.isEmpty()) {
            throw new IllegalArgumentException("Waste category '" + dto.getWasteCategory() + "' does not exist. Please create it first.");
        }

        // Map DTO to Entity
        DisposalGuideline guideline = new DisposalGuideline();
        guideline.setGuideline(dto.getGuideline());
        guideline.setWasteCategory(dto.getWasteCategory());

        // Save and return
        return repository.save(guideline);
    }

    public DisposalGuideline updateGuideline(Long id, DisposalGuideline updatedGuideline) {
        DisposalGuideline guideline = getGuidelineById(id);
        guideline.setGuideline(updatedGuideline.getGuideline());
        guideline.setWasteCategory(updatedGuideline.getWasteCategory());
        return repository.save(guideline);
    }

    public void deleteGuideline(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Disposal Guideline with ID " + id + " not found");
        }
        repository.deleteById(id);
    }
    
}
