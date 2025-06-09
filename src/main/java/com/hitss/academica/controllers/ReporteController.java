package com.hitss.academica.controllers;

import com.hitss.academica.dto.reporte.HistorialAcademicoDTO;
import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO;
import com.hitss.academica.dto.reporte.ReporteFinalDTO;
import com.hitss.academica.services.ReporteService;
import com.hitss.academica.utils.CsvExportService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints para la generación y exportación de reportes académicos")
@SecurityRequirement(name = "bearerAuth")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private CsvExportService csvExportService;

    // --- Endpoints JSON ---

    @GetMapping("/notas-promedio")
    @Operation(summary = "Obtener promedio de notas por asignatura (JSON)", description = "Devuelve el promedio de notas para cada asignatura en formato JSON.")
    public ResponseEntity<List<PromedioAsignaturaDTO>> getPromedioNotas() {
        return ResponseEntity.ok(reporteService.getPromedioNotasPorAsignatura());
    }

    @GetMapping("/historial-estudiante/{id}")
    @Operation(summary = "Obtener historial académico de un estudiante (JSON)", description = "Devuelve el historial completo de notas de un estudiante en formato JSON.")
    public ResponseEntity<HistorialAcademicoDTO> getHistorialEstudiante(@Parameter(description = "ID del estudiante") @PathVariable Long id) {
        return ResponseEntity.ok(reporteService.getHistorialEstudiante(id));
    }

    @GetMapping("/reporte-final/{cursoId}")
    @Operation(summary = "Obtener reporte final de curso (JSON)", description = "Genera una 'sábana' de notas para todos los estudiantes de un curso en formato JSON.")
    public ResponseEntity<ReporteFinalDTO> getReporteFinal(@Parameter(description = "ID del curso") @PathVariable Long cursoId) {
        return ResponseEntity.ok(reporteService.getReporteFinalCurso(cursoId));
    }

    // --- Endpoints CSV ---

    @GetMapping("/notas-promedio/csv")
    @Operation(summary = "Exportar promedio de notas (CSV)", description = "Descarga un archivo CSV con el promedio de notas para cada asignatura.")
    public void exportPromedioNotasToCsv(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte_promedios.csv\"");
        List<PromedioAsignaturaDTO> data = reporteService.getPromedioNotasPorAsignatura();
        csvExportService.writeDataToCsv(response.getWriter(), data);
    }

    @GetMapping("/reporte-final/{cursoId}/csv")
    @Operation(summary = "Exportar reporte final de curso (CSV)", description = "Descarga un archivo CSV con la 'sábana' de notas de un curso.")
    public void exportReporteFinalToCsv(@Parameter(description = "ID del curso") @PathVariable Long cursoId, HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte_final_curso_" + cursoId + ".csv\"");
        ReporteFinalDTO data = reporteService.getReporteFinalCurso(cursoId);
        // El DTO EstudianteReporteDTO no se mapea bien automáticamente, así que lo haríamos manual con writeRawData
        // Por ahora, lo dejamos pendiente para una mejora futura si es necesario.
        csvExportService.writeDataToCsv(response.getWriter(), data.getResultadosEstudiantes());
    }

    // --- NUEVO ENDPOINT ---
    @GetMapping("/historial-estudiante/{id}/csv")
    @Operation(summary = "Exportar historial académico de un estudiante (CSV)", description = "Descarga un archivo CSV con las notas de un estudiante.")
    public void exportHistorialEstudianteToCsv(
            @Parameter(description = "ID del estudiante") @PathVariable Long id, HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        // 1. Obtenemos el DTO completo del historial
        HistorialAcademicoDTO historial = reporteService.getHistorialEstudiante(id);

        // 2. Configuramos la respuesta HTTP para la descarga del archivo
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"historial_" + historial.getCodigoMatricula() + ".csv\"");

        // 3. Escribimos información de cabecera en el CSV (opcional pero útil)
        PrintWriter writer = response.getWriter();
        writer.println("REPORTE DE HISTORIAL ACADEMICO");
        writer.println("Estudiante:," + historial.getNombreEstudiante()); // La coma separa en columnas
        writer.println("Codigo Matricula:," + historial.getCodigoMatricula());
        writer.println("Curso Actual:," + historial.getCursoActual());
        writer.println(); // Línea en blanco para separar
        // "Flusheamos" para asegurarnos de que se escriba
        writer.flush();

        // 4. Usamos nuestro servicio para escribir la lista de notas
        // El DTO 'NotaResponseDTO' tiene campos planos y se mapeará perfectamente.
        csvExportService.writeDataToCsv(writer, historial.getNotas());
    }
}