package com.enviro.assessment.grad001.thabangsoulo.service;

import com.enviro.assessment.grad001.thabangsoulo.dto.RecyclingTipDTO;
import com.enviro.assessment.grad001.thabangsoulo.entity.RecyclingTip;
import com.enviro.assessment.grad001.thabangsoulo.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.thabangsoulo.repository.RecyclingTipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecyclingTipService {
    private final RecyclingTipRepository repository;

    public RecyclingTipService(RecyclingTipRepository repository) {
        this.repository = repository;
    }

    public List<RecyclingTip> getAllTips() {
        return repository.findAll();
    }

    public RecyclingTip getTipById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recycling Tip with ID " + id + " not found"));
    }


    public RecyclingTip createTip(RecyclingTipDTO dto) {
        RecyclingTip tip = new RecyclingTip();
        tip.setTip(dto.getTip());
        tip.setRelevance(dto.getRelevance());
        return repository.save(tip);
    }

    public RecyclingTip updateTip(Long id, RecyclingTip updatedTip) {
        RecyclingTip tip = getTipById(id);
        tip.setTip(updatedTip.getTip());
        tip.setRelevance(updatedTip.getRelevance());
        return repository.save(tip);
    }

    public void deleteTip(Long id) {
        repository.deleteById(id);
    }
}
