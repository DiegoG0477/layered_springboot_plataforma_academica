package com.hitss.academica.dto.auth;

import lombok.Data;

@Data
public class MeResponseDTO {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String email;
    private String rol;
    private Long profesorId;
    private Long estudianteId;
}