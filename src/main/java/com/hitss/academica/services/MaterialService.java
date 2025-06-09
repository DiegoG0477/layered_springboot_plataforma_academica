package com.hitss.academica.services;

import com.hitss.academica.dto.material.MaterialRequestDTO;
import com.hitss.academica.dto.material.MaterialResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialService {
    MaterialResponseDTO create(MaterialRequestDTO requestDTO, MultipartFile archivo) throws IOException;
    void delete(Long id) throws IOException;
    List<MaterialResponseDTO> findByAsignaturaId(Long asignaturaId);
}