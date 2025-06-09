package com.hitss.academica.services;

import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorDetailResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorRequestDTO;
import com.hitss.academica.dto.profesor.ProfesorResponseDTO;

import java.util.List;

public interface ProfesorService {
    /**
     * Crea un nuevo perfil de profesor para un usuario existente.
     * @param requestDTO Contiene el ID del usuario y su especialidad.
     * @return El DTO del profesor creado.
     */
    ProfesorResponseDTO create(ProfesorRequestDTO requestDTO);

    /**
     * Obtiene una lista de todos los profesores.
     * @return Lista de DTOs de profesor.
     */
    List<ProfesorResponseDTO> findAll();

    /**
     * Busca un profesor por su ID y devuelve sus detalles.
     * @param id El ID del profesor.
     * @return El DTO detallado del profesor.
     */
    ProfesorDetailResponseDTO findById(Long id);

    /**
     * Obtiene todas las asignaturas impartidas por un profesor específico.
     * @param profesorId El ID del profesor.
     * @return Lista de DTOs de asignatura.
     */
    List<AsignaturaResponseDTO> findAsignaturasByProfesorId(Long profesorId);
}