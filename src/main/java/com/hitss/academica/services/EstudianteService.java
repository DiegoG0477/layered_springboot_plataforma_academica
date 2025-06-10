package com.hitss.academica.services;

import com.hitss.academica.dto.estudiante.EstudianteDetailResponseDTO;
import com.hitss.academica.dto.estudiante.EstudianteRequestDTO;
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO;

import java.util.List;

public interface EstudianteService {
    EstudianteResponseDTO create(EstudianteRequestDTO requestDTO);
    List<EstudianteResponseDTO> findAll();
    EstudianteDetailResponseDTO findById(Long id);
    List<EstudianteResponseDTO> findByCurso(Long cursoId);
    List<EstudianteResponseDTO> findByAsignatura(Long asignaturaId);
}