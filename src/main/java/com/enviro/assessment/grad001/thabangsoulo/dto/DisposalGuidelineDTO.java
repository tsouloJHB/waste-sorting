package com.enviro.assessment.grad001.thabangsoulo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DisposalGuidelineDTO {

    @NotBlank(message = "Guideline cannot be empty")
    private String guideline;

    @NotBlank(message = "Waste category cannot be empty")
    private String wasteCategory;
}
