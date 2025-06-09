package com.hitss.academica.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoRequestDTO {
    @NotBlank(message = "El nombre del curso no puede estar vacío")
    private String nombre;

    @NotNull(message = "El ID del periodo académico no puede ser nulo")
    private Long periodoAcademicoId;
}