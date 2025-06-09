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

    // Nuevo mapper para el endpoint /me
    @Mapping(source = "rol.rol", target = "rol")
    MeResponseDTO usuarioToMeResponseDto(Usuario usuario);
}