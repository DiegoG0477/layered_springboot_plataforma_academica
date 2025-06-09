package com.hitss.academica.dto.auth;

import com.hitss.academica.repositories.UsuarioRepository;
import com.hitss.academica.validation.UniqueValue;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Size(min = 2, max = 45, message = "El apellido paterno debe tener entre 2 y 45 caracteres")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno no puede estar vacío")
    @Size(min = 2, max = 45, message = "El apellido materno debe tener entre 2 y 45 caracteres")
    private String apellidoMaterno;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 45, message = "El correo electrónico no debe exceder los 45 caracteres")
    @UniqueValue(repository = UsuarioRepository.class, fieldName = "email", message = "El correo electrónico ya está registrado.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El ID del rol no puede ser nulo")
    private Long rolId;
}