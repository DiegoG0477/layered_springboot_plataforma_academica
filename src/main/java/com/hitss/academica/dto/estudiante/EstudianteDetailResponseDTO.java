package com.hitss.academica.dto.estudiante;

import lombok.Data;

@Data
public class EstudianteDetailResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String codigoMatricula;
    private String cursoActual;
}