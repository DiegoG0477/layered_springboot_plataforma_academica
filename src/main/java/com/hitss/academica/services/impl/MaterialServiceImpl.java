package com.hitss.academica.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hitss.academica.dto.material.MaterialRequestDTO;
import com.hitss.academica.dto.material.MaterialResponseDTO;
import com.hitss.academica.entities.Asignatura;
import com.hitss.academica.entities.Material;
import com.hitss.academica.mappers.MaterialMapper;
import com.hitss.academica.repositories.AsignaturaRepository;
import com.hitss.academica.repositories.MaterialRepository;
import com.hitss.academica.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    @Transactional
    public MaterialResponseDTO create(MaterialRequestDTO requestDTO, MultipartFile archivo) throws IOException {
        Asignatura asignatura = asignaturaRepository.findById(requestDTO.getAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + requestDTO.getAsignaturaId()));

        // 1. Subir el archivo a Cloudinary
        Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), ObjectUtils.emptyMap());
        String url = (String) uploadResult.get("secure_url"); // O "url" si no usas https

        // 2. Crear y guardar la entidad Material con la URL obtenida
        Material nuevoMaterial = new Material();
        nuevoMaterial.setTitulo(requestDTO.getTitulo());
        nuevoMaterial.setDescripcion(requestDTO.getDescripcion());
        nuevoMaterial.setAsignatura(asignatura);
        nuevoMaterial.setArchivoUrl(url);

        Material materialGuardado = materialRepository.save(nuevoMaterial);
        return materialMapper.materialToMaterialResponseDto(materialGuardado);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IOException {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con ID: " + id));

        // 1. Extraer el public_id de la URL para poder borrarlo de Cloudinary
        String url = material.getArchivoUrl();
        String publicId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));

        // 2. Borrar el archivo de Cloudinary
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        // 3. Borrar el registro de la base de datos (borrado lógico)
        materialRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> findByAsignaturaId(Long asignaturaId) {
        if (!asignaturaRepository.existsById(asignaturaId)) {
            throw new RuntimeException("Asignatura no encontrada con ID: " + asignaturaId);
        }
        List<Material> materiales = materialRepository.findByAsignaturaId(asignaturaId);
        return materialMapper.materialsToMaterialResponseDtos(materiales);
    }
}