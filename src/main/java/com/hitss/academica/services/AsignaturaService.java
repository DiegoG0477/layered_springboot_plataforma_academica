package com.hitss.academica.services;

import com.hitss.academica.dto.asignatura.AsignaturaDetailResponseDTO;
import com.hitss.academica.dto.asignatura.AsignaturaRequestDTO;
import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;

import java.util.List;

public interface AsignaturaService {
    AsignaturaResponseDTO create(AsignaturaRequestDTO requestDTO);

    List<AsignaturaResponseDTO> findAll();

    AsignaturaDetailResponseDTO findById(Long id);

    AsignaturaResponseDTO update(Long id, AsignaturaRequestDTO requestDTO);

    void delete(Long id);

    List<AsignaturaResponseDTO> findAsignaturasByEstudianteAuth(String userEmail);
}