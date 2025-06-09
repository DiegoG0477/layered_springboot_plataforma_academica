package com.hitss.academica.services;

import com.hitss.academica.dto.reporte.HistorialAcademicoDTO;
import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO;
import com.hitss.academica.dto.reporte.ReporteFinalDTO;
import java.util.List;

public interface ReporteService {
    List<PromedioAsignaturaDTO> getPromedioNotasPorAsignatura();

    HistorialAcademicoDTO getHistorialEstudiante(Long estudianteId);

    ReporteFinalDTO getReporteFinalCurso(Long cursoId);
}