package com.hitss.academica.dto.profesor;

import lombok.Data;

@Data
public class ProfesorResponseDTO {
    private Long id;
    private String nombreCompleto; // Combinaremos nombre y apellidos
    private String especialidad;
    private String email;
}