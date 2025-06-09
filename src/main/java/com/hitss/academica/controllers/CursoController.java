package com.hitss.academica.controllers;

import com.hitss.academica.dto.curso.CursoDetailResponseDTO;
import com.hitss.academica.dto.curso.CursoRequestDTO;
import com.hitss.academica.dto.curso.CursoResponseDTO;
import com.hitss.academica.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> createCurso(@Valid @RequestBody CursoRequestDTO requestDTO) {
        CursoResponseDTO nuevoCurso = cursoService.create(requestDTO);
        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> getAllCursos() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDetailResponseDTO> getCursoById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }
}