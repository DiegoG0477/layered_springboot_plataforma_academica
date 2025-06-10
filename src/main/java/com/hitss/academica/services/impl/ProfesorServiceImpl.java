package com.hitss.academica.services.impl;

import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorDetailResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorRequestDTO;
import com.hitss.academica.dto.profesor.ProfesorResponseDTO;
import com.hitss.academica.entities.Profesor;
import com.hitss.academica.entities.Usuario;
import com.hitss.academica.mappers.AsignaturaMapper;
import com.hitss.academica.mappers.ProfesorMapper;
import com.hitss.academica.repositories.AsignaturaRepository;
import com.hitss.academica.repositories.ProfesorRepository;
import com.hitss.academica.repositories.UsuarioRepository;
import com.hitss.academica.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private ProfesorMapper profesorMapper;

    @Autowired
    private AsignaturaMapper asignaturaMapper;

    @Override
    @Transactional
    public ProfesorResponseDTO create(ProfesorRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + requestDTO.getUsuarioId()));

        if (!"ROLE_PROFESOR".equals(usuario.getRol().getRol())) {
            throw new IllegalArgumentException("El usuario proporcionado no tiene el rol de PROFESOR.");
        }

        Profesor nuevoProfesor = new Profesor();
        nuevoProfesor.setUsuario(usuario);
        nuevoProfesor.setEspecialidad(requestDTO.getEspecialidad());
        
        Profesor profesorGuardado = profesorRepository.save(nuevoProfesor);
        
        return profesorMapper.profesorToProfesorResponseDto(profesorGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResponseDTO> findAll() {
        return profesorRepository.findAll().stream()
                .map(profesorMapper::profesorToProfesorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProfesorDetailResponseDTO findById(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));

        List<String> nombresAsignaturas = asignaturaRepository.findByProfesorId(id).stream()
                .map(asignatura -> asignatura.getNombre())
                .collect(Collectors.toList());

        ProfesorDetailResponseDTO dto = new ProfesorDetailResponseDTO();
        dto.setId(profesor.getId());
        dto.setNombreCompleto(profesor.getUsuario().getNombre() + " " + profesor.getUsuario().getApellidoPaterno());
        dto.setEmail(profesor.getUsuario().getEmail());
        dto.setEspecialidad(profesor.getEspecialidad());
        // Añadimos la lista que obtuvimos
        dto.setAsignaturas(nombresAsignaturas);
        
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaResponseDTO> findAsignaturasByProfesorId(Long profesorId) {
        // Validar que el profesor exista
        if (!profesorRepository.existsById(profesorId)) {
            throw new RuntimeException("Profesor no encontrado con ID: " + profesorId);
        }
        
        return asignaturaRepository.findByProfesorId(profesorId).stream()
                .map(asignaturaMapper::asignaturaToAsignaturaResponseDto)
                .collect(Collectors.toList());
    }
}