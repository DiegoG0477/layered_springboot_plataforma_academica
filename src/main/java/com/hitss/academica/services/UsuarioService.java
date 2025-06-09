package com.hitss.academica.services;

import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.dto.usuario.UsuarioUpdateRequestDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO register(RegisterRequestDTO registerRequestDTO);

    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO update(Long id, UsuarioUpdateRequestDTO updateRequestDTO);

    void delete(Long id);
}