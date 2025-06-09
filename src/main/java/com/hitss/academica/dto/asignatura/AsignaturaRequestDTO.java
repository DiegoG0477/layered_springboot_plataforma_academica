package com.hitss.academica.dto.asignatura;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignaturaRequestDTO {

    @NotBlank(message = "El nombre de la asignatura no puede estar vacío")
    private String nombre;

    @NotNull(message = "El ID del profesor no puede ser nulo")
    private Long profesorId;

    @NotNull(message = "El ID del curso no puede ser nulo")
    private Long cursoId;
}