package com.hitss.academica.services;

import com.hitss.academica.dto.nota.NotaRequestDTO;
import com.hitss.academica.dto.nota.NotaResponseDTO;

import java.util.List;

public interface NotaService {
    NotaResponseDTO create(NotaRequestDTO requestDTO);
    NotaResponseDTO update(Long id, NotaRequestDTO requestDTO);
    void delete(Long id);
    List<NotaResponseDTO> findByAsignaturaId(Long asignaturaId);
    List<NotaResponseDTO> findByEstudianteId(Long estudianteId);
    List<NotaResponseDTO> findNotasByEstudianteAuth(String userEmail);
}