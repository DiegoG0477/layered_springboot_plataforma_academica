package com.hitss.academica.dto.periodo;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @FutureOrPresent(message = "La fecha de fin no puede ser en el pasado")
    private LocalDate fechaFin;
}