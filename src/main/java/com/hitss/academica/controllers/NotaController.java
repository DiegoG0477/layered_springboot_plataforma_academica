package com.hitss.academica.controllers;

import com.hitss.academica.dto.nota.NotaRequestDTO;
import com.hitss.academica.dto.nota.NotaResponseDTO;
import com.hitss.academica.services.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping
    public ResponseEntity<NotaResponseDTO> createNota(@Valid @RequestBody NotaRequestDTO requestDTO) {
        return new ResponseEntity<>(notaService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/asignatura/{id}")
    public ResponseEntity<List<NotaResponseDTO>> getNotasByAsignatura(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.findByAsignaturaId(id));
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<NotaResponseDTO>> getNotasByEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.findByEstudianteId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> updateNota(@PathVariable Long id, @Valid @RequestBody NotaRequestDTO requestDTO) {
        return ResponseEntity.ok(notaService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        notaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estudiante")
    @Operation(summary = "Obtener mis calificaciones",
               description = "Devuelve la lista de todas las calificaciones del estudiante autenticado.")
    public ResponseEntity<List<NotaResponseDTO>> getMisNotas(Authentication authentication) {
        String userEmail = authentication.getName();
        List<NotaResponseDTO> notas = notaService.findNotasByEstudianteAuth(userEmail);
        return ResponseEntity.ok(notas);
    }
}