package com.enviro.assessment.grad001.thabangsoulo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recycling_tips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tip is mandatory")
    private String tip;

    private String relevance; // For optional fields like "Plastic" or "Metal"
}
