package com.hitss.academica.controllers;

import com.hitss.academica.dto.periodo.PeriodoRequestDTO;
import com.hitss.academica.dto.periodo.PeriodoResponseDTO;
import com.hitss.academica.services.PeriodoLectivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periodos")
public class PeriodoLectivoController {

    @Autowired
    private PeriodoLectivoService periodoLectivoService;

    @PostMapping
    public ResponseEntity<PeriodoResponseDTO> createPeriodo(@Valid @RequestBody PeriodoRequestDTO requestDTO) {
        PeriodoResponseDTO nuevoPeriodo = periodoLectivoService.create(requestDTO);
        return new ResponseEntity<>(nuevoPeriodo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PeriodoResponseDTO>> getAllPeriodos() {
        return ResponseEntity.ok(periodoLectivoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodoResponseDTO> getPeriodoById(@PathVariable Long id) {
        return ResponseEntity.ok(periodoLectivoService.findById(id));
    }
}