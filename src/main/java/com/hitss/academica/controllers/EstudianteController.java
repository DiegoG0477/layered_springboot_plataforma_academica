package com.hitss.academica.controllers;

import com.hitss.academica.dto.estudiante.EstudianteDetailResponseDTO;
import com.hitss.academica.dto.estudiante.EstudianteRequestDTO;
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO;
import com.hitss.academica.services.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> createEstudiante(@Valid @RequestBody EstudianteRequestDTO requestDTO) {
        return new ResponseEntity<>(estudianteService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> getAllEstudiantes() {
        return ResponseEntity.ok(estudianteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDetailResponseDTO> getEstudianteById(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.findById(id));
    }
}