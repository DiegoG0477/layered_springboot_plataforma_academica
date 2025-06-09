package com.hitss.academica.controllers;

import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.dto.usuario.UsuarioUpdateRequestDTO;
import com.hitss.academica.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.register(registerRequestDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }


    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequestDTO updateRequestDTO) {
        return ResponseEntity.ok(usuarioService.update(id, updateRequestDTO));
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        // Devolvemos 204 No Content, que es el estándar para un DELETE exitoso.
        return ResponseEntity.noContent().build();
    }
}