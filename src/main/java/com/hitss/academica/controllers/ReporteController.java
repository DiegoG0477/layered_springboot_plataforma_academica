package com.hitss.academica.controllers;

import com.hitss.academica.dto.reporte.HistorialAcademicoDTO;
import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO;
import com.hitss.academica.dto.reporte.ReporteFinalDTO;
import com.hitss.academica.services.ReporteService;
import com.hitss.academica.utils.CsvExportService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints para la generación de reportes académicos")
@SecurityRequirement(name = "bearerAuth")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private CsvExportService csvExportService;

    // --- Endpoints JSON ---

    @Operation(summary = "Obtener promedio de notas (JSON)", description = "Devuelve el promedio de notas para cada asignatura en formato JSON.")
    @GetMapping("/notas-promedio")
    public ResponseEntity<List<PromedioAsignaturaDTO>> getPromedioNotas() {
        return ResponseEntity.ok(reporteService.getPromedioNotasPorAsignatura());
    }

    @Operation(summary = "Obtener historial académico (JSON)", description = "Devuelve el historial completo de notas de un estudiante en formato JSON.")
    @GetMapping("/historial-estudiante/{id}")
    public ResponseEntity<HistorialAcademicoDTO> getHistorialEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.getHistorialEstudiante(id));
    }

    @Operation(summary = "Obtener reporte final de curso (JSON)", description = "Genera una 'sábana' de notas para un curso en formato JSON.")
    @GetMapping("/reporte-final/{cursoId}")
    public ResponseEntity<ReporteFinalDTO> getReporteFinal(@PathVariable Long cursoId) {
        return ResponseEntity.ok(reporteService.getReporteFinalCurso(cursoId));
    }

    @Operation(summary = "Exportar promedio de notas (CSV)", description = "Descarga un archivo CSV con el promedio de notas para cada asignatura.")
    @GetMapping("/notas-promedio/csv")
    public void exportPromedioNotasToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte_promedios.csv\"");

        List<PromedioAsignaturaDTO> data = reporteService.getPromedioNotasPorAsignatura();

        response.getWriter().write(csvExportService.writeDataToCsv(data));
    }

    @Operation(summary = "Exportar reporte final de curso (CSV)", description = "Descarga un archivo CSV con la 'sábana' de notas de un curso.")
    @GetMapping("/reporte-final/{cursoId}/csv")
    public void exportReporteFinalToCsv(@PathVariable Long cursoId, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte_final_curso_" + cursoId + ".csv\"");

        ReporteFinalDTO data = reporteService.getReporteFinalCurso(cursoId);

        response.getWriter().write(csvExportService.writeDataToCsv(data.getResultadosEstudiantes()));
    }
}