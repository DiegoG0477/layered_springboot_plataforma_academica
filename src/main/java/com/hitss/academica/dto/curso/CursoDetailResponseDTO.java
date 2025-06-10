package com.hitss.academica.dto.curso;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CursoDetailResponseDTO {
    private Long id;
    private String nombre;
    private String periodoAcademico;
    private LocalDateTime createdAt;
}