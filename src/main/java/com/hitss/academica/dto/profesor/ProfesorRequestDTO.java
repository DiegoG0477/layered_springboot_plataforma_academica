package com.hitss.academica.dto.profesor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfesorRequestDTO {
    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidad;
}