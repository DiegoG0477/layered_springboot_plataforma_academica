package com.hitss.academica.services;

import com.hitss.academica.dto.rol.RolResponseDTO;

import java.util.List;

public interface RolService {
    /**
     * Obtiene una lista de todos los roles.
     * @return Lista de DTOs de rol.
     */
    List<RolResponseDTO> findAll();
}