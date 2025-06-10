package com.hitss.academica.services;

import com.hitss.academica.dto.rol.RolResponseDTO;

import java.util.List;

public interface RolService {

    List<RolResponseDTO> findAll();
}