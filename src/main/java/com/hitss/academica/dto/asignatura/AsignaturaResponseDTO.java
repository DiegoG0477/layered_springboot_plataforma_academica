package com.hitss.academica.dto.asignatura;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AsignaturaResponseDTO {
    private Long id;
    private String nombre;
    private Long profesorId;
    private Long cursoId;
    private LocalDateTime createdAt;
}