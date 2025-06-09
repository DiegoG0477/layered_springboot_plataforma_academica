package com.hitss.academica.mappers;

import com.hitss.academica.dto.nota.NotaResponseDTO;
import com.hitss.academica.entities.Nota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotaMapper {

    @Mapping(target = "nombreEstudiante", expression = "java(nota.getEstudiante().getUsuario().getNombre() + \" \" + nota.getEstudiante().getUsuario().getApellidoPaterno())")
    @Mapping(source = "asignatura.nombre", target = "nombreAsignatura")
    NotaResponseDTO notaToNotaResponseDto(Nota nota);

    List<NotaResponseDTO> notasToNotaResponseDtos(List<Nota> notas);
}