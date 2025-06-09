package com.hitss.academica.mappers;

import com.hitss.academica.dto.curso.CursoDetailResponseDTO;
import com.hitss.academica.dto.curso.CursoResponseDTO;
import com.hitss.academica.entities.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(source = "periodoLectivo.id", target = "periodoAcademicoId")
    CursoResponseDTO cursoToCursoResponseDto(Curso curso);

    @Mapping(source = "periodoLectivo.nombre", target = "periodoAcademico")
    CursoDetailResponseDTO cursoToCursoDetailDto(Curso curso);
}