package com.enviro.assessment.grad001.thabangsoulo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disposal_guidelines")
@Data // Generates getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class DisposalGuideline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Guideline cannot be empty")
    private String guideline;

    @NotBlank(message = "Waste category cannot be empty")
    private String wasteCategory;
}
