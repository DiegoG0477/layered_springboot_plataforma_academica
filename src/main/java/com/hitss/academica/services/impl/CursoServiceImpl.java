package com.hitss.academica.services.impl;

import com.hitss.academica.dto.curso.CursoDetailResponseDTO;
import com.hitss.academica.dto.curso.CursoRequestDTO;
import com.hitss.academica.dto.curso.CursoResponseDTO;
import com.hitss.academica.entities.Curso;
import com.hitss.academica.entities.PeriodoLectivo;
import com.hitss.academica.mappers.CursoMapper;
import com.hitss.academica.repositories.CursoRepository;
import com.hitss.academica.repositories.PeriodoLectivoRepository;
import com.hitss.academica.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PeriodoLectivoRepository periodoLectivoRepository;

    @Autowired
    private CursoMapper cursoMapper;

    @Override
    @Transactional
    public CursoResponseDTO create(CursoRequestDTO requestDTO) {
        PeriodoLectivo periodo = periodoLectivoRepository.findById(requestDTO.getPeriodoAcademicoId())
                .orElseThrow(() -> new RuntimeException("Periodo Lectivo no encontrado con ID: " + requestDTO.getPeriodoAcademicoId()));

        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombre(requestDTO.getNombre());
        nuevoCurso.setPeriodoLectivo(periodo);

        Curso cursoGuardado = cursoRepository.save(nuevoCurso);
        return cursoMapper.cursoToCursoResponseDto(cursoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> findAll() {
        return cursoRepository.findAll().stream()
                .map(cursoMapper::cursoToCursoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursoDetailResponseDTO findById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        return cursoMapper.cursoToCursoDetailDto(curso);
    }
}