package com.hitss.academica.controllers;

import com.hitss.academica.dto.material.MaterialRequestDTO;
import com.hitss.academica.dto.material.MaterialResponseDTO;
import com.hitss.academica.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialResponseDTO> createMaterial(
            @Valid @RequestPart("data") MaterialRequestDTO requestDTO,
            @RequestPart("file") MultipartFile archivo) throws IOException {

        return new ResponseEntity<>(materialService.create(requestDTO, archivo), HttpStatus.CREATED);
    }

    @GetMapping("/asignatura/{id}")
    public ResponseEntity<List<MaterialResponseDTO>> getMaterialesByAsignatura(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.findByAsignaturaId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) throws IOException {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}