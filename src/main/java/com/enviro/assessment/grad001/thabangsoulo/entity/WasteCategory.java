package com.enviro.assessment.grad001.thabangsoulo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "waste_categories",
    uniqueConstraints = @UniqueConstraint(columnNames = "name") // Add a unique constraint at the table level
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true) // Ensure the name is unique at the column level
    private String name;

    private String description;
}

