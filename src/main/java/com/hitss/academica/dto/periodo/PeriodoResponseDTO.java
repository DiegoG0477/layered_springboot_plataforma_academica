package com.hitss.academica.dto.periodo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PeriodoResponseDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}