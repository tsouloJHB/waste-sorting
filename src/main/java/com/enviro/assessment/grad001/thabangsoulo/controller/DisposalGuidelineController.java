package com.enviro.assessment.grad001.thabangsoulo.controller;

import com.enviro.assessment.grad001.thabangsoulo.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.thabangsoulo.service.DisposalGuidelineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disposal-guidelines")
public class DisposalGuidelineController {

    private final DisposalGuidelineService service;

    public DisposalGuidelineController(DisposalGuidelineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DisposalGuideline>> getAllGuidelines() {
        return ResponseEntity.ok(service.getAllGuidelines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuideline> getGuidelineById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGuidelineById(id));
    }

    @PostMapping
    public ResponseEntity<DisposalGuideline> createGuideline(@Valid @RequestBody DisposalGuidelineDTO dto) {
        DisposalGuideline savedGuideline = service.createGuideline(dto);
        return ResponseEntity.ok(savedGuideline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuideline> updateGuideline(@PathVariable Long id, @Valid @RequestBody DisposalGuideline guideline) {
        return ResponseEntity.ok(service.updateGuideline(id, guideline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuideline(@PathVariable Long id) {
        service.deleteGuideline(id);
        return ResponseEntity.noContent().build();
    }
}
