package com.hitss.academica.mappers;

import com.hitss.academica.dto.asignatura.AsignaturaDetailResponseDTO;
import com.hitss.academica.dto.asignatura.AsignaturaResponseDTO;
import com.hitss.academica.entities.Asignatura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsignaturaMapper {

    @Mapping(source = "profesor.id", target = "profesorId")
    @Mapping(source = "curso.id", target = "cursoId")
    AsignaturaResponseDTO asignaturaToAsignaturaResponseDto(Asignatura asignatura);


    @Mapping(source = "profesor.usuario.nombre", target = "nombreProfesor")
    @Mapping(source = "curso.nombre", target = "nombreCurso")
    @Mapping(source = "curso.id", target = "cursoId")
    AsignaturaDetailResponseDTO asignaturaToAsignaturaDetailResponseDto(Asignatura asignatura);
}