package com.hitss.academica.mappers;

import com.hitss.academica.dto.profesor.ProfesorDetailResponseDTO;
import com.hitss.academica.dto.profesor.ProfesorResponseDTO;
import com.hitss.academica.entities.Asignatura;
import com.hitss.academica.entities.Profesor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProfesorMapper {

    // Mapeo para la lista
    @Mapping(target = "nombreCompleto", expression = "java(profesor.getUsuario().getNombre() + \" \" + profesor.getUsuario().getApellidoPaterno())")
    @Mapping(source = "usuario.email", target = "email")
    ProfesorResponseDTO profesorToProfesorResponseDto(Profesor profesor);

    // Mapeo para el detalle
    // Necesitamos una lista de asignaturas para el profesor, que no está directamente en la entidad 'Profesor'.
    // Esta información la proporcionará el servicio. Por eso, el DTO de detalle no se puede mapear automáticamente.
    // Crearemos un método personalizado en el servicio para esto.
}