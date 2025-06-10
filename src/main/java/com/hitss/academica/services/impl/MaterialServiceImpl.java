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
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

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

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "application/pdf",
            "application/msword", // .doc
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" // .docx
    );

    @Override
    @Transactional
    public MaterialResponseDTO create(MaterialRequestDTO requestDTO, MultipartFile archivo) throws IOException {
        // --- INICIO DE VALIDACIÓN ---
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío.");
        }

        String contentType = archivo.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se aceptan: JPG, PNG, PDF, DOC, DOCX.");
        }
        // --- FIN DE VALIDACIÓN ---

        Asignatura asignatura = asignaturaRepository.findById(requestDTO.getAsignaturaId())
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con ID: " + requestDTO.getAsignaturaId()));

        // Para Cloudinary, es útil subir los archivos a una carpeta específica
        Map<String, String> options = ObjectUtils.asMap(
            "folder", "academica/materiales", //carpeta de cloudinary
            "resource_type", "auto" // cloudinary detecta en automatico tipo de archivo
        );

        Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), options);
        String url = (String) uploadResult.get("secure_url");

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