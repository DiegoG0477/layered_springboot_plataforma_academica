package com.hitss.academica.services;

import com.hitss.academica.dto.estudiante.EstudianteDetailResponseDTO;
import com.hitss.academica.dto.estudiante.EstudianteRequestDTO;
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO;
// Importar DTO de Nota si es necesario

import java.util.List;

public interface EstudianteService {
    EstudianteResponseDTO create(EstudianteRequestDTO requestDTO);
    List<EstudianteResponseDTO> findAll();
    EstudianteDetailResponseDTO findById(Long id);
}