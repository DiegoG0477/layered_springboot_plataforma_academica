package com.hitss.academica.services;

import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorDetailResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorRequestDTO;
import com.hitss.academica.dto.profesor.ProfesorResponseDTO;

import java.util.List;

public interface ProfesorService {

    ProfesorResponseDTO create(ProfesorRequestDTO requestDTO);

    List<ProfesorResponseDTO> findAll();

    ProfesorDetailResponseDTO findById(Long id);

    List<AsignaturaResponseDTO> findAsignaturasByProfesorId(Long profesorId);
}