package com.hitss.academica.controllers;

import com.hitss.academica.dto.rol.RolResponseDTO;
import com.hitss.academica.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(rolService.findAll());
    }
}