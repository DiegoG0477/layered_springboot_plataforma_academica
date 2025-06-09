package com.hitss.academica.dto.profesor;

import lombok.Data;
import java.util.List;

@Data
public class ProfesorDetailResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String especialidad;
    private String email;
    private List<String> asignaturas; // JOIN - Lista de nombres de asignaturas
}