package com.enviro.assessment.grad001.thabangsoulo.controller;

import com.enviro.assessment.grad001.thabangsoulo.dto.RecyclingTipDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.RecyclingTip;
import com.enviro.assessment.grad001.thabangsoulo.service.RecyclingTipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recycling-tips")
public class RecyclingTipController {

    private final RecyclingTipService service;

    public RecyclingTipController(RecyclingTipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecyclingTip>> getAllTips() {
        return ResponseEntity.ok(service.getAllTips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTip> getTipById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTipById(id));
    }

       @PostMapping
    public ResponseEntity<RecyclingTip> createTip(@Valid @RequestBody RecyclingTipDTO dto) {
        RecyclingTip tip = service.createTip(dto);
        return ResponseEntity.ok(tip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTip> updateTip(@PathVariable Long id, @Valid @RequestBody RecyclingTip tip) {
        return ResponseEntity.ok(service.updateTip(id, tip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        service.deleteTip(id);
        return ResponseEntity.noContent().build();
    }
}
