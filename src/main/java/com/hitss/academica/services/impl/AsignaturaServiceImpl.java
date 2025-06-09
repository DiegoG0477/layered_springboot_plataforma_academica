package com.hitss.academica.services.impl;

import com.hitss.academica.dto.asignatura.AsignaturaDetailResponseDTO;
import com.hitss.academica.dto.asignatura.AsignaturaRequestDTO;
import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.entities.Asignatura;
import com.hitss.academica.entities.Curso;
import com.hitss.academica.entities.Profesor;
import com.hitss.academica.mappers.AsignaturaMapper;
import com.hitss.academica.repositories.AsignaturaRepository;
import com.hitss.academica.repositories.CursoRepository;
import com.hitss.academica.repositories.ProfesorRepository;
import com.hitss.academica.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AsignaturaMapper asignaturaMapper;

    @Override
    @Transactional
    public AsignaturaResponseDTO create(AsignaturaRequestDTO requestDTO) {
        // 1. Validar que el profesor y el curso existan
        Profesor profesor = profesorRepository.findById(requestDTO.getProfesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + requestDTO.getProfesorId()));

        Curso curso = cursoRepository.findById(requestDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + requestDTO.getCursoId()));

        // 2. Crear y guardar la nueva asignatura
        Asignatura nuevaAsignatura = new Asignatura();
        nuevaAsignatura.setNombre(requestDTO.getNombre());
        nuevaAsignatura.setProfesor(profesor);
        nuevaAsignatura.setCurso(curso);

        Asignatura asignaturaGuardada = asignaturaRepository.save(nuevaAsignatura);

        return asignaturaMapper.asignaturaToAsignaturaResponseDto(asignaturaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaResponseDTO> findAll() {
        return asignaturaRepository.findAll().stream()
                .map(asignaturaMapper::asignaturaToAsignaturaResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AsignaturaDetailResponseDTO findById(Long id) {
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
        return asignaturaMapper.asignaturaToAsignaturaDetailResponseDto(asignatura);
    }

    @Override
    @Transactional
    public AsignaturaResponseDTO update(Long id, AsignaturaRequestDTO requestDTO) {
        // 1. Buscar la asignatura existente
        Asignatura asignaturaAActualizar = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));

        // 2. Validar que las nuevas referencias (profesor, curso) existan
        Profesor profesor = profesorRepository.findById(requestDTO.getProfesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + requestDTO.getProfesorId()));

        Curso curso = cursoRepository.findById(requestDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + requestDTO.getCursoId()));

        // 3. Actualizar los campos
        asignaturaAActualizar.setNombre(requestDTO.getNombre());
        asignaturaAActualizar.setProfesor(profesor);
        asignaturaAActualizar.setCurso(curso);

        // 4. Guardar y devolver
        Asignatura asignaturaActualizada = asignaturaRepository.save(asignaturaAActualizar);
        return asignaturaMapper.asignaturaToAsignaturaResponseDto(asignaturaActualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada con ID: " + id);
        }
        // Borrado lógico gracias a @SQLDelete
        asignaturaRepository.deleteById(id);
    }
}