package com.hitss.academica.mappers;

import com.hitss.academica.dto.periodo.PeriodoRequestDTO;
import com.hitss.academica.dto.periodo.PeriodoResponseDTO;
import com.hitss.academica.entities.PeriodoLectivo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeriodoMapper {
    PeriodoLectivo periodoRequestDtoToPeriodoLectivo(PeriodoRequestDTO dto);
    PeriodoResponseDTO periodoLectivoToPeriodoResponseDto(PeriodoLectivo periodo);
}