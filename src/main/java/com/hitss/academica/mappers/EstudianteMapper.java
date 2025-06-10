package com.hitss.academica.mappers;

import com.hitss.academica.dto.estudiante.EstudianteDetailResponseDTO;
import com.hitss.academica.dto.estudiante.EstudianteResponseDTO;
import com.hitss.academica.entities.Estudiante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    @Mapping(target = "nombreCompleto", expression = "java(estudiante.getUsuario().getNombre() + \" \" + estudiante.getUsuario().getApellidoPaterno())")
    @Mapping(source = "cursoActual.id", target = "cursoActualId")
    EstudianteResponseDTO estudianteToEstudianteResponseDto(Estudiante estudiante);

    @Mapping(target = "nombreCompleto", expression = "java(estudiante.getUsuario().getNombre() + \" \" + estudiante.getUsuario().getApellidoPaterno())")
    @Mapping(source = "usuario.email", target = "email")
    @Mapping(source = "cursoActual.nombre", target = "cursoActual")
    EstudianteDetailResponseDTO estudianteToEstudianteDetailDto(Estudiante estudiante);

    List<EstudianteResponseDTO> estudiantesToEstudianteResponseDtos(List<Estudiante> estudiantes);
}