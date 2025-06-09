package com.hitss.academica.mappers;

import com.hitss.academica.dto.rol.RolResponseDTO;
import com.hitss.academica.entities.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolResponseDTO rolToRolResponseDto(Rol rol);
    List<RolResponseDTO> rolesToRolResponseDtos(List<Rol> roles);
}