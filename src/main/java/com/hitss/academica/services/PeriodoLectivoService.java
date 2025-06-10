package com.hitss.academica.services;

import com.hitss.academica.dto.periodo.PeriodoRequestDTO;
import com.hitss.academica.dto.periodo.PeriodoResponseDTO;

import java.util.List;

public interface PeriodoLectivoService {
    PeriodoResponseDTO create(PeriodoRequestDTO requestDTO);

    List<PeriodoResponseDTO> findAll();

    PeriodoResponseDTO findById(Long id);
}