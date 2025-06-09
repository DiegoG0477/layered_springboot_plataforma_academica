package com.hitss.academica.dto.usuario;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String rol; // Nombre del rol
    private LocalDateTime createdAt;
}