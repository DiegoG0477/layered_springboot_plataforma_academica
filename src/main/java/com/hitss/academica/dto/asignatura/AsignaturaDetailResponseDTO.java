package com.hitss.academica.dto.asignatura;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AsignaturaDetailResponseDTO {
    private Long id;
    private String nombre;
    private String nombreProfesor;
    private String nombreCurso;
    private Long cursoId;
    private LocalDateTime createdAt;
}