package com.hitss.academica.mappers;

import com.hitss.academica.dto.material.MaterialResponseDTO;
import com.hitss.academica.entities.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    @Mapping(source = "asignatura.id", target = "asignaturaId")
    MaterialResponseDTO materialToMaterialResponseDto(Material material);

    List<MaterialResponseDTO> materialsToMaterialResponseDtos(List<Material> materials);
}