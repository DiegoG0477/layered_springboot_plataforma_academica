package com.hitss.academica.dto.matricula;

import com.hitss.academica.dto.curso.CursoResponseDTO; // Reutilizamos DTOs!
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO; // Reutilizamos DTOs!
import lombok.Data;

@Data
public class MatriculaDetailResponseDTO {
    private Long id;
    private EstudianteResponseDTO estudiante;
    private CursoResponseDTO curso;
}