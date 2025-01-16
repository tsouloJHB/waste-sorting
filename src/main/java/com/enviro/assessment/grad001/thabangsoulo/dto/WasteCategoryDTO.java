package com.enviro.assessment.grad001.thabangsoulo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasteCategoryDTO {
    
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
}
