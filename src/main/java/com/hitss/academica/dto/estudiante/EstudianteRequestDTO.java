package com.hitss.academica.dto.estudiante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstudianteRequestDTO {
    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "El ID del curso actual no puede ser nulo")
    private Long cursoActualId;

    @NotBlank(message = "El código de matrícula no puede estar vacío")
    private String codigoMatricula;
}