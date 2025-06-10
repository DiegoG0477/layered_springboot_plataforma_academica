package com.hitss.academica.services.impl;

import com.hitss.academica.dto.nota.NotaRequestDTO;
import com.hitss.academica.dto.nota.NotaResponseDTO;
import com.hitss.academica.entities.Asignatura;
import com.hitss.academica.entities.Estudiante;
import com.hitss.academica.entities.Nota;
import com.hitss.academica.mappers.NotaMapper;
import com.hitss.academica.repositories.AsignaturaRepository;
import com.hitss.academica.repositories.EstudianteRepository;
import com.hitss.academica.repositories.NotaRepository;
import com.hitss.academica.services.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private NotaMapper notaMapper;

    @Override
    @Transactional
    public NotaResponseDTO create(NotaRequestDTO requestDTO) {
        Estudiante estudiante = estudianteRepository.findById(requestDTO.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + requestDTO.getEstudianteId()));

        Asignatura asignatura = asignaturaRepository.findById(requestDTO.getAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + requestDTO.getAsignaturaId()));

        if (!estudiante.getCursoActual().getId().equals(asignatura.getCurso().getId())) {
            throw new IllegalArgumentException("El estudiante no pertenece al curso de esta asignatura.");
        }

        Nota nuevaNota = new Nota();
        nuevaNota.setEstudiante(estudiante);
        nuevaNota.setAsignatura(asignatura);
        nuevaNota.setValor(requestDTO.getValor());
        nuevaNota.setObservaciones(requestDTO.getObservaciones());

        Nota notaGuardada = notaRepository.save(nuevaNota);
        return notaMapper.notaToNotaResponseDto(notaGuardada);
    }

    @Override
    @Transactional
    public NotaResponseDTO update(Long id, NotaRequestDTO requestDTO) {
        Nota notaExistente = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con ID: " + id));

        notaExistente.setValor(requestDTO.getValor());
        notaExistente.setObservaciones(requestDTO.getObservaciones());

        Nota notaActualizada = notaRepository.save(notaExistente);
        return notaMapper.notaToNotaResponseDto(notaActualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota no encontrada con ID: " + id);
        }
        notaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotaResponseDTO> findByAsignaturaId(Long asignaturaId) {
        if (!asignaturaRepository.existsById(asignaturaId)) {
            throw new RuntimeException("Asignatura no encontrada con ID: " + asignaturaId);
        }
        List<Nota> notas = notaRepository.findByAsignaturaId(asignaturaId);
        return notaMapper.notasToNotaResponseDtos(notas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotaResponseDTO> findByEstudianteId(Long estudianteId) {

        if (!estudianteRepository.existsById(estudianteId)) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + estudianteId);
        }
        List<Nota> notas = notaRepository.findByEstudianteId(estudianteId);
        return notaMapper.notasToNotaResponseDtos(notas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotaResponseDTO> findNotasByEstudianteAuth(String userEmail) {
        // La lógica está encapsulada en el repositorio.
        List<Nota> notas = notaRepository.findByEstudiante_Usuario_Email(userEmail);
        return notaMapper.notasToNotaResponseDtos(notas);
    }
}