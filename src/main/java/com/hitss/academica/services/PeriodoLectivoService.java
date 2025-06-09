package com.hitss.academica.services;

import com.hitss.academica.dto.periodo.PeriodoRequestDTO;
import com.hitss.academica.dto.periodo.PeriodoResponseDTO;

import java.util.List;

public interface PeriodoLectivoService {
    /**
     * Crea un nuevo periodo lectivo.
     * @param requestDTO Los datos del periodo a crear.
     * @return El DTO del periodo recién creado.
     */
    PeriodoResponseDTO create(PeriodoRequestDTO requestDTO);

    /**
     * Obtiene una lista de todos los periodos lectivos.
     * @return Lista de DTOs de periodo.
     */
    List<PeriodoResponseDTO> findAll();

    /**
     * Busca un periodo lectivo por su ID.
     * @param id El ID del periodo.
     * @return El DTO del periodo encontrado.
     */
    PeriodoResponseDTO findById(Long id);
}