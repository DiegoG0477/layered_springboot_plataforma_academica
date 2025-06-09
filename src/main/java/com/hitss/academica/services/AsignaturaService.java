package com.hitss.academica.services;

import com.hitss.academica.dto.asignatura.AsignaturaDetailResponseDTO;
import com.hitss.academica.dto.asignatura.AsignaturaRequestDTO;
import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;

import java.util.List;

public interface AsignaturaService {
    /**
     * Crea una nueva asignatura.
     * @param requestDTO Datos para la nueva asignatura.
     * @return DTO de la asignatura creada.
     */
    AsignaturaResponseDTO create(AsignaturaRequestDTO requestDTO);

    /**
     * Obtiene una lista de todas las asignaturas.
     * @return Lista de DTOs de asignatura.
     */
    List<AsignaturaResponseDTO> findAll();

    /**
     * Busca una asignatura por su ID y devuelve sus detalles.
     * @param id El ID de la asignatura.
     * @return DTO detallado de la asignatura.
     */
    AsignaturaDetailResponseDTO findById(Long id);

    /**
     * Actualiza una asignatura existente.
     * @param id El ID de la asignatura a actualizar.
     * @param requestDTO Los nuevos datos para la asignatura.
     * @return DTO de la asignatura actualizada.
     */
    AsignaturaResponseDTO update(Long id, AsignaturaRequestDTO requestDTO);

    /**
     * Elimina una asignatura (borrado lógico).
     * @param id El ID de la asignatura a eliminar.
     */
    void delete(Long id);
}