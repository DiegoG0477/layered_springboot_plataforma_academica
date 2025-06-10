package com.hitss.academica.services;

import com.hitss.academica.dto.curso.CursoDetailResponseDTO;
import com.hitss.academica.dto.curso.CursoRequestDTO;
import com.hitss.academica.dto.curso.CursoResponseDTO;

import java.util.List;

public interface CursoService {
    CursoResponseDTO create(CursoRequestDTO requestDTO);
    List<CursoResponseDTO> findAll();
    CursoDetailResponseDTO findById(Long id);
}