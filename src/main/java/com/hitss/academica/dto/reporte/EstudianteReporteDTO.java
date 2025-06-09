package com.hitss.academica.dto.reporte;

import lombok.Data;
import java.util.Map;

@Data
public class EstudianteReporteDTO {
    private Long estudianteId;
    private String nombreEstudiante;
    private Map<String, Integer> notasPorAsignatura;
}