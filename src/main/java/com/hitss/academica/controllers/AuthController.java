package com.hitss.academica.controllers;

import com.hitss.academica.dto.auth.LoginRequestDTO;
import com.hitss.academica.dto.auth.MeResponseDTO;
import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.entities.Usuario;
import com.hitss.academica.mappers.UsuarioMapper;
import com.hitss.academica.repositories.UsuarioRepository;
import com.hitss.academica.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Operation(
        summary = "Iniciar sesión de usuario",
        description = "Autentica a un usuario con su email y contraseña, y devuelve un token JWT si es exitoso.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales del usuario",
            required = true,
            content = @Content(schema = @Schema(implementation = LoginRequestDTO.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = com.hitss.academica.dto.auth.LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/login")
    public void fakeLoginEndpoint() {
        // solo existe para que swagger pueda documentarlo.
        throw new IllegalStateException("Este método no debería ser llamado. Es manejado por Spring Security.");
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String userEmail = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Error interno: Usuario del token no encontrado."));

        MeResponseDTO meResponse = usuarioMapper.usuarioToMeResponseDto(usuario);
        return ResponseEntity.ok(meResponse);
    }
}