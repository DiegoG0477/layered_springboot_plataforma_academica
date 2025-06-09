package com.hitss.academica.controllers;

import com.hitss.academica.dto.asignatura.AsignaturaDetailResponseDTO;
import com.hitss.academica.dto.asignatura.AsignaturaRequestDTO;
import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.services.AsignaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @PostMapping
    public ResponseEntity<AsignaturaResponseDTO> createAsignatura(@Valid @RequestBody AsignaturaRequestDTO requestDTO) {
        return new ResponseEntity<>(asignaturaService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AsignaturaResponseDTO>> getAllAsignaturas() {
        return ResponseEntity.ok(asignaturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDetailResponseDTO> getAsignaturaById(@PathVariable Long id) {
        return ResponseEntity.ok(asignaturaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaResponseDTO> updateAsignatura(@PathVariable Long id, @Valid @RequestBody AsignaturaRequestDTO requestDTO) {
        return ResponseEntity.ok(asignaturaService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Long id) {
        asignaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}