package com.hitss.academica.controllers;

import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorDetailResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorRequestDTO;
import com.hitss.academica.dto.profesor.ProfesorResponseDTO;
import com.hitss.academica.services.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<ProfesorResponseDTO> createProfesor(@Valid @RequestBody ProfesorRequestDTO requestDTO) {
        return new ResponseEntity<>(profesorService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfesorResponseDTO>> getAllProfesores() {
        return ResponseEntity.ok(profesorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDetailResponseDTO> getProfesorById(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.findById(id));
    }

    @GetMapping("/{id}/asignaturas")
    public ResponseEntity<List<AsignaturaResponseDTO>> getAsignaturasByProfesor(@PathVariable("id") Long profesorId) {
        return ResponseEntity.ok(profesorService.findAsignaturasByProfesorId(profesorId));
    }
}