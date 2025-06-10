package com.hitss.academica.services.impl;

import com.hitss.academica.dto.estudiante.EstudianteDetailResponseDTO;
import com.hitss.academica.dto.estudiante.EstudianteRequestDTO;
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO;
import com.hitss.academica.entities.Curso;
import com.hitss.academica.entities.Estudiante;
import com.hitss.academica.entities.Usuario;
import com.hitss.academica.mappers.EstudianteMapper;
import com.hitss.academica.repositories.AsignaturaRepository; // <-- Importación añadida
import com.hitss.academica.repositories.CursoRepository;
import com.hitss.academica.repositories.EstudianteRepository;
import com.hitss.academica.repositories.UsuarioRepository;
import com.hitss.academica.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException; // <-- Importación añadida
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository; // <-- Dependencia inyectada

    @Autowired
    private EstudianteMapper estudianteMapper;

    @Override
    @Transactional
    public EstudianteResponseDTO create(EstudianteRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + requestDTO.getUsuarioId()));

        if (!"ROLE_ESTUDIANTE".equals(usuario.getRol().getRol())) {
            throw new IllegalArgumentException("El usuario proporcionado no tiene el rol de ESTUDIANTE.");
        }

        if (estudianteRepository.existsByCodigoMatricula(requestDTO.getCodigoMatricula())) {
             throw new IllegalArgumentException("El código de matrícula ya existe.");
        }

        Curso cursoActual = cursoRepository.findById(requestDTO.getCursoActualId())
                .orElseThrow(() -> new NoSuchElementException("Curso no encontrado con ID: " + requestDTO.getCursoActualId()));

        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setUsuario(usuario);
        nuevoEstudiante.setCursoActual(cursoActual);
        nuevoEstudiante.setCodigoMatricula(requestDTO.getCodigoMatricula());

        Estudiante estudianteGuardado = estudianteRepository.save(nuevoEstudiante);
        return estudianteMapper.estudianteToEstudianteResponseDto(estudianteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll() {
        return estudianteRepository.findAll().stream()
                .map(estudianteMapper::estudianteToEstudianteResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteDetailResponseDTO findById(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado con ID: " + id));
        return estudianteMapper.estudianteToEstudianteDetailDto(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findByCurso(Long cursoId) {
        List<Estudiante> estudiantes = estudianteRepository.findAllByCursoActualId(cursoId);
        return estudianteMapper.estudiantesToEstudianteResponseDtos(estudiantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findByAsignatura(Long asignaturaId) {
        if (!asignaturaRepository.existsById(asignaturaId)) {
            throw new NoSuchElementException("Asignatura no encontrada con ID: " + asignaturaId);
        }
        List<Estudiante> estudiantes = estudianteRepository.findByAsignaturaId(asignaturaId);
        return estudianteMapper.estudiantesToEstudianteResponseDtos(estudiantes);
    }
}