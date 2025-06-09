package com.hitss.academica.dto.nota;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotaResponseDTO {
    private Long id;
    private String nombreEstudiante;
    private String nombreAsignatura;
    private Integer valor;
    private String observaciones;
    private LocalDateTime createdAt;
}