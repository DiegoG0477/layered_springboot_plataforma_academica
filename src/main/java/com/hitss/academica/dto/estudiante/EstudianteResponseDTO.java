package com.hitss.academica.dto.estudiante;

import lombok.Data;

@Data
public class EstudianteResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String codigoMatricula;
    private Long cursoActualId;
}