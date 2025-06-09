package com.hitss.academica.dto.auth;

import lombok.Data;

@Data
public class MeResponseDTO {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String email;
    private String rol;
    // Podríamos añadir una lista de permisos específicos si los tuviéramos
    // private List<String> permissions;
}