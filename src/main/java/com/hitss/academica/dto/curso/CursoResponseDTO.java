package com.hitss.academica.dto.curso;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CursoResponseDTO {
    private Long id;
    private String nombre;
    private Long periodoAcademicoId;
    private LocalDateTime createdAt;
}