package com.hitss.academica.dto.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialRequestDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El ID de la asignatura no puede ser nulo")
    private Long asignaturaId;
}