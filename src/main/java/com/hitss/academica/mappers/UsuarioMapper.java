package com.hitss.academica.mappers;

import com.hitss.academica.dto.auth.MeResponseDTO;
import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Ya lo teníamos
    Usuario registerRequestDtoToUsuario(RegisterRequestDTO registerRequestDTO);

    // Ya lo teníamos
    @Mapping(source = "rol.rol", target = "rol")
    UsuarioResponseDTO usuarioToUsuarioResponseDto(Usuario usuario);

    // Para las listas
    List<UsuarioResponseDTO> usuariosToUsuarioResponseDtos(List<Usuario> usuarios);

    @Mapping(source = "rol.rol", target = "rol")
    @Mapping(source = "profesor.id", target = "profesorId")
    @Mapping(source = "estudiante.id", target = "estudianteId")
    MeResponseDTO usuarioToMeResponseDto(Usuario usuario);
}