package com.hitss.academica.controllers;

import com.hitss.academica.dto.reporte.HistorialAcademicoDTO;
import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO; // <-- Importar el DTO
import com.hitss.academica.dto.reporte.ReporteFinalDTO;
import com.hitss.academica.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints para la generación de reportes académicos")
@SecurityRequirement(name = "bearerAuth")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Obtener promedio de notas por asignatura", description = "Devuelve el promedio de notas para cada asignatura de cada curso. Requiere rol de ADMIN.")
    @GetMapping("/notas-promedio")
    public ResponseEntity<List<PromedioAsignaturaDTO>> getPromedioNotas() { // <-- TIPO CORREGIDO
        return ResponseEntity.ok(reporteService.getPromedioNotasPorAsignatura());
    }

    @Operation(summary = "Obtener historial académico de un estudiante", description = "Devuelve el historial completo de notas de un estudiante. Requiere rol de ADMIN.")
    @GetMapping("/historial-estudiante/{id}")
    public ResponseEntity<HistorialAcademicoDTO> getHistorialEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.getHistorialEstudiante(id));
    }

    @Operation(summary = "Obtener reporte final consolidado por curso", description = "Genera una 'sábana' de notas con todos los estudiantes y sus calificaciones para un curso específico. Requiere rol de ADMIN.")
    @GetMapping("/reporte-final/{cursoId}")
    public ResponseEntity<ReporteFinalDTO> getReporteFinal(@PathVariable Long cursoId) {
        return ResponseEntity.ok(reporteService.getReporteFinalCurso(cursoId));
    }
}