package com.hitss.academica.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioUpdateRequestDTO {
    @Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
    private String nombre;

    @Size(min = 2, max = 45, message = "El apellido paterno debe tener entre 2 y 45 caracteres")
    private String apellidoPaterno;

    @Size(min = 2, max = 45, message = "El apellido materno debe tener entre 2 y 45 caracteres")
    private String apellidoMaterno;

    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 45, message = "El correo electrónico no debe exceder los 45 caracteres")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private Long rolId;
}