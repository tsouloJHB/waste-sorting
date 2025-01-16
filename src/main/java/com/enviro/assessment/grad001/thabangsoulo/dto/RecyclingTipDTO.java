package com.enviro.assessment.grad001.thabangsoulo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingTipDTO {
    @NotBlank(message = "Tip is mandatory")
    private String tip;

    private String relevance; // Optional field for specifying relevance (e.g., "Plastic" or "Metal")
}
