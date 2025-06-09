package com.hitss.academica.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // @Data de Lombok genera getters, setters, toString, equals, hashCode
public class LoginRequestDTO {

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}