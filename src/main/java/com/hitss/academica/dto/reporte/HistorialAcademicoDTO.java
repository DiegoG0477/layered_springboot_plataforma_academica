package com.hitss.academica.dto.reporte;

import com.hitss.academica.dto.nota.NotaResponseDTO; // Reutilizaremos este DTO
import lombok.Data;
import java.util.List;

@Data
public class HistorialAcademicoDTO {
    private Long estudianteId;
    private String nombreEstudiante;
    private String codigoMatricula;
    private String cursoActual;
    private List<NotaResponseDTO> notas;
}