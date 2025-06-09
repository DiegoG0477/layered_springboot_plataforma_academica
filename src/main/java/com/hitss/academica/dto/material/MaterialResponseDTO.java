package com.hitss.academica.dto.material;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String archivoUrl;
    private Long asignaturaId;
    private LocalDateTime createdAt;
}