package com.hitss.academica.dto.reporte;

import lombok.Data;
import java.util.List;

@Data
public class ReporteFinalDTO {
    private Long cursoId;
    private String nombreCurso;
    private String periodoLectivo;
    private List<EstudianteReporteDTO> resultadosEstudiantes;
}