package com.hitss.academica.dto.nota;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotaRequestDTO {

    @NotNull(message = "El ID del estudiante no puede ser nulo")
    private Long estudianteId;

    @NotNull(message = "El ID de la asignatura no puede ser nulo")
    private Long asignaturaId;

    @NotNull(message = "El valor de la nota no puede ser nulo")
    @Min(value = 10, message = "La nota no puede ser menor que 0")
    @Max(value = 100, message = "La nota no puede ser mayor que 100")
    private Integer valor;

    private String observaciones;
}