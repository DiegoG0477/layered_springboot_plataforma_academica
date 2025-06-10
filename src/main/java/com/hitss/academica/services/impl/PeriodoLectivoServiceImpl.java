package com.hitss.academica.services.impl;

import com.hitss.academica.dto.periodo.PeriodoRequestDTO;
import com.hitss.academica.dto.periodo.PeriodoResponseDTO;
import com.hitss.academica.entities.PeriodoLectivo;
import com.hitss.academica.mappers.PeriodoMapper;
import com.hitss.academica.repositories.PeriodoLectivoRepository;
import com.hitss.academica.services.PeriodoLectivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodoLectivoServiceImpl implements PeriodoLectivoService {

    @Autowired
    private PeriodoLectivoRepository periodoLectivoRepository;

    @Autowired
    private PeriodoMapper periodoMapper;

    @Override
    @Transactional
    public PeriodoResponseDTO create(PeriodoRequestDTO requestDTO) {
        if (requestDTO.getFechaInicio().isAfter(requestDTO.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        PeriodoLectivo nuevoPeriodo = periodoMapper.periodoRequestDtoToPeriodoLectivo(requestDTO);
        PeriodoLectivo periodoGuardado = periodoLectivoRepository.save(nuevoPeriodo);
        return periodoMapper.periodoLectivoToPeriodoResponseDto(periodoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeriodoResponseDTO> findAll() {
        return periodoLectivoRepository.findAll().stream()
                .map(periodoMapper::periodoLectivoToPeriodoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodoResponseDTO findById(Long id) {
        PeriodoLectivo periodo = periodoLectivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Periodo Lectivo no encontrado con ID: " + id));
        return periodoMapper.periodoLectivoToPeriodoResponseDto(periodo);
    }
}