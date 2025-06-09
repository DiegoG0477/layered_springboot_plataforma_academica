package com.hitss.academica.dto.reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromedioAsignaturaDTO {
    private String nombreCurso;
    private Long asignaturaId;
    private String nombreAsignatura;
    private Double notaPromedio;
}